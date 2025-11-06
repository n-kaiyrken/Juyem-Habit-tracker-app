package kz.nkaiyrken.juyem.features.habits.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekDates
import kz.nkaiyrken.juyem.core.util.DateUtils.getWeekStart
import kz.nkaiyrken.juyem.core.util.isFuture
import kz.nkaiyrken.juyem.core.util.isPast
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.CreateHabitUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.GetHabitsForWeekUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.progress.GetProgressForWeekUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.progress.UpsertDailyProgressUseCase
import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitAction
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.NumericProgress
import org.jetbrains.annotations.VisibleForTesting
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    getHabitsForWeekUseCase: GetHabitsForWeekUseCase,
    getProgressForWeekUseCase: GetProgressForWeekUseCase,
    private val upsertDailyProgressUseCase: UpsertDailyProgressUseCase,
    createHabitUseCase: CreateHabitUseCase,
) : ViewModel() {

    private val _currentWeekStartDate = MutableStateFlow(getWeekStart(LocalDate.now()))

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HabitListUiState> =
        _currentWeekStartDate.flatMapLatest { weekStartDate ->
            combine(
                getHabitsForWeekUseCase(weekStartDate),
                getProgressForWeekUseCase(weekStartDate),
            ) { habits, progress ->
                HabitListUiState(
                    contentState = when {
                        habits.isEmpty() -> HabitListUiState.ContentState.Empty
                        else -> HabitListUiState.ContentState.Success(
                            habits = mapToHabitsCardUiModels(
                                habits = habits,
                                weekProgress = progress
                            )
                        )
                    },
                    currentWeekStartDate = _currentWeekStartDate.value,
                )
            }
        }
            .catch { error ->
                emit(
                    HabitListUiState(
                        contentState = HabitListUiState.ContentState.Error(
                            message = error.message ?: "Unknown error occurred"
                        ),
                        currentWeekStartDate = _currentWeekStartDate.value,
                    )
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HabitListUiState(currentWeekStartDate = getWeekStart(LocalDate.now())),
            )

    init {
//        viewModelScope.launch {
//            createHabitUseCase(
//                Habit(
//                    id = 0,
//                    title = "Drink water",
//                    orderIndex = 1,
//                    type = HabitType.BOOLEAN,
//                )
//            )
//            createHabitUseCase(
//                Habit(
//                    id = 1,
//                    title = "Exercise",
//                    orderIndex = 0,
//                    type = HabitType.COUNTER,
//                    goalValue = 30,
//                    unit = "подходов",
//                )
//            )
//            createHabitUseCase(
//                Habit(
//                    id = 2,
//                    title = "Read a book",
//                    orderIndex = 2,
//                    type = HabitType.TIMER,
//                    goalValue = 360,
//                    unit = "минут",
//                )
//            )

    }

    /**
     * Handles all habit card actions in a type-safe manner
     */
    fun handleAction(action: HabitAction) {
        when (action) {
            is HabitAction.Complete -> handleMarkComplete(action.dailyProgressUiModel)
            is HabitAction.ConfirmCounter -> handleConfirmCounter(
                action.dailyProgressUiModel as DailyProgressUiModel.Counter,
                action.count
            )
            is HabitAction.StartTimer -> handleStartTimer(action.dailyProgressUiModel.habitId)
            is HabitAction.EditNote -> handleEditNote(action.dailyProgressUiModel.habitId)
            is HabitAction.Skip -> handleSkip(action.dailyProgressUiModel)
            is HabitAction.Clear -> handleClear(action.dailyProgressUiModel)
            is HabitAction.DayChipClick -> handleDayClick(action.dailyProgressUiModel)
        }
    }

    fun onPreviousWeekClick() {
        _currentWeekStartDate.update { it.minusWeeks(1) }
    }

    fun onNextWeekClick() {
        _currentWeekStartDate.update { it.plusWeeks(1) }
    }

    /**
     * Maps domain models (Habit + DailyProgress) to UI model for displaying in habit card
     *
     * Handles schedule changes correctly:
     * - Days with progress always shown (even if removed from schedule)
     * - Past days without progress are disabled (were added to schedule later)
     * - Future/today days in schedule are enabled for interaction
     */
    @VisibleForTesting
    private fun mapToHabitsCardUiModels(
        habits: List<Habit>,
        weekProgress: Map<Int, Map<LocalDate, DailyProgress>>,
    ): List<HabitCardUiModel> {
        val weekDates = getWeekDates(_currentWeekStartDate.value)
        val today = LocalDate.now()

        return habits.map { habit ->
            val habitProgress = weekProgress[habit.id] ?: emptyMap()
            val weekDaysProgress = weekDates.associate { date ->
                date.dayOfWeek to createDayProgressUiModel(
                    habit = habit,
                    date = date,
                    progress = habitProgress[date],
                    today = today
                )
            }

            HabitCardUiModel(
                habitId = habit.id,
                title = habit.title,
                habitType = habit.type,
                weekDaysProgress = weekDaysProgress,
            )
        }
    }

    private fun mapDailyProgressUiModelToDailyProgress(dailyProgressUiModel: DailyProgressUiModel): DailyProgress {
        return when (dailyProgressUiModel) {
            is DailyProgressUiModel.TypeBoolean -> DailyProgress(
                habitId = dailyProgressUiModel.habitId,
                date = dailyProgressUiModel.date,
                status = dailyProgressUiModel.status,
            )

            is DailyProgressUiModel.Counter -> DailyProgress(
                habitId = dailyProgressUiModel.habitId,
                date = dailyProgressUiModel.date,
                status = dailyProgressUiModel.status,
                value = dailyProgressUiModel.currentValue,
            )

            is DailyProgressUiModel.Timer -> DailyProgress(
                habitId = dailyProgressUiModel.habitId,
                date = dailyProgressUiModel.date,
                status = dailyProgressUiModel.status,
                value = dailyProgressUiModel.currentValue,
            )
        }
    }

    /**
     * Creates progress UI model for a single day based on context
     *
     * Priority order:
     * 1. Existing data → show and enable
     * 2. Before creation → disable
     * 3. Not in schedule → disable
     * 4. Today/Future → enable
     * 5. Past without data → disable (added to schedule later)
     */
    private fun createDayProgressUiModel(
        habit: Habit,
        date: LocalDate,
        progress: DailyProgress?,
        today: LocalDate,
    ): DailyProgressUiModel {
        return when {
            // Has existing progress data - always show with actual status
            progress != null -> createDailyProgressUiModelFromExistingEntity(
                habit = habit,
                progress = progress,
                isEnabled = true // User can edit existing records
            )

            // Day before habit creation
            date.isBefore(habit.createdAt.toLocalDate()) -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = false,
                reason = "Before habit creation"
            )

            // Day not in current schedule
            !habit.daysOfWeek.contains(date.dayOfWeek) -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = false,
                reason = "Not in schedule"
            )

            // Today in schedule
            date == today -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = true,
                reason = "Today"
            )

            // Future day in schedule
            date.isFuture() -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = true,
                reason = "Future"
            )

            // Past day in schedule without progress (was added to schedule later)
            date.isPast() -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = false,
                reason = "Added to schedule later"
            )

            else -> createEmptyDailyProgressUiModel(
                habit = habit,
                date = date,
                isEnabled = false,
                reason = "Unknown"
            )
        }
    }

    internal fun createEmptyDailyProgressUiModel(
        habit: Habit,
        date: LocalDate,
        isEnabled: Boolean,
        reason: String, // For debugging/logging
        status: DailyProgressStatus = DailyProgressStatus.EMPTY,
    ): DailyProgressUiModel {
        return when (habit.type) {
            HabitType.BOOLEAN -> DailyProgressUiModel.TypeBoolean(
                habitId = habit.id,
                date = date,
                status = status,
                isEnabled = isEnabled,
            )

            HabitType.COUNTER -> DailyProgressUiModel.Counter(
                currentValue = 0,
                goalValue = habit.goalValue ?: 0,
                unit = habit.unit ?: "",
                habitId = habit.id,
                date = date,
                status = status,
                isEnabled = isEnabled,
            )

            HabitType.TIMER -> DailyProgressUiModel.Timer(
                currentValue = 0,
                goalValue = habit.goalValue ?: 0,
                habitId = habit.id,
                date = date,
                status = status,
                isEnabled = isEnabled,
            )
        }
    }

    private fun createDailyProgressUiModelFromExistingEntity(
        habit: Habit,
        progress: DailyProgress,
        isEnabled: Boolean = true,
    ): DailyProgressUiModel {
        return when (habit.type) {
            HabitType.BOOLEAN -> DailyProgressUiModel.TypeBoolean(
                habitId = progress.habitId,
                date = progress.date,
                status = progress.status,
                isEnabled = isEnabled,
            )

            HabitType.COUNTER -> DailyProgressUiModel.Counter(
                currentValue = progress.value,
                goalValue = habit.goalValue ?: 0,
                unit = habit.unit ?: "",
                habitId = progress.habitId,
                date = progress.date,
                status = progress.status,
                isEnabled = isEnabled,
            )

            HabitType.TIMER -> DailyProgressUiModel.Timer(
                currentValue = progress.value,
                goalValue = habit.goalValue ?: 0,
                habitId = progress.habitId,
                date = progress.date,
                status = progress.status,
                isEnabled = isEnabled,
            )
        }
    }

    private fun handleMarkComplete(dailyProgressUiModel: DailyProgressUiModel) {
        viewModelScope.launch {
            val newValue = when (dailyProgressUiModel) {
                is NumericProgress -> {
                    if (dailyProgressUiModel.currentValue < dailyProgressUiModel.goalValue)
                        dailyProgressUiModel.goalValue
                    else
                        dailyProgressUiModel.currentValue
                }
                else -> 0
            }
            upsertDailyProgressUseCase(
                mapDailyProgressUiModelToDailyProgress(dailyProgressUiModel)
                    .copy(
                        status = DailyProgressStatus.COMPLETED,
                        value = newValue
                    )
            )
        }
    }

    private fun handleConfirmCounter(
        dailyProgressUiModel: DailyProgressUiModel.Counter,
        count: Int,
    ) {
        viewModelScope.launch {
            val newStatus = when {
                count >= dailyProgressUiModel.goalValue -> DailyProgressStatus.COMPLETED
                count < dailyProgressUiModel.goalValue && dailyProgressUiModel.date.isPast() -> DailyProgressStatus.FAILED
                else -> DailyProgressStatus.EMPTY
            }
            upsertDailyProgressUseCase(
                mapDailyProgressUiModelToDailyProgress(dailyProgressUiModel)
                    .copy(
                        status = newStatus,
                        value = count,
                    )
            )
        }
    }

    private fun handleStartTimer(habitId: Int) {
        // TODO: Implement - start timer for timer habit
    }

    private fun handleEditNote(habitId: Int) {
        // TODO: Implement - open note editing dialog/screen
    }

    private fun handleSkip(dailyProgressUiModel: DailyProgressUiModel) {
        viewModelScope.launch {
            upsertDailyProgressUseCase(
                mapDailyProgressUiModelToDailyProgress(dailyProgressUiModel)
                    .copy(status = DailyProgressStatus.SKIPPED)
            )
        }
    }

    private fun handleClear(dailyProgressUiModel: DailyProgressUiModel) {
        viewModelScope.launch {
            val newStatus = when {
                dailyProgressUiModel.date.isPast() -> DailyProgressStatus.FAILED
                else -> DailyProgressStatus.EMPTY
            }

            upsertDailyProgressUseCase(
                mapDailyProgressUiModelToDailyProgress(dailyProgressUiModel)
                    .copy(
                        status = newStatus,
                        value = 0,
                    )
            )
        }
    }

    private fun handleDayClick(dailyProgressUiModel: DailyProgressUiModel) {
        // TODO: Implement - handle click on a specific day (view/edit past progress)
    }
}


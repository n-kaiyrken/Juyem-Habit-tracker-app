package kz.nkaiyrken.juyem.features.habits.presentation.createhabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.CreateHabitUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.GetActiveHabitsUseCase
import java.time.DayOfWeek
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateHabitViewModel @Inject constructor(
    private val createHabitUseCase: CreateHabitUseCase,
    private val getActiveHabitsUseCase: GetActiveHabitsUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateHabitUiState())
    val uiState: StateFlow<CreateHabitUiState> = _uiState.asStateFlow()

    fun onTitleChange(title: String) {
        _uiState.update { state ->
            state.copy(
                title = title,
                titleError = null
            )
        }
    }

    fun onHabitTypeChange(habitType: HabitType) {
        _uiState.update { state ->
            state.copy(
                habitType = habitType,
                goalValue = "",
                goalValueError = null,
                selectedUnit = when (habitType) {
                    HabitType.TIMER -> MeasurementUnit.MINUTES
                    else -> null
                }
            )
        }
    }

    fun onGoalValueChange(value: String) {
        val filteredValue = value.filter { it.isDigit() }
        val currentState = _uiState.value

        val error = validateGoalValue(filteredValue, currentState.maxGoalValue)

        _uiState.update { state ->
            state.copy(
                goalValue = filteredValue,
                goalValueError = error
            )
        }
    }

    fun onUnitSelected(unit: MeasurementUnit?) {
        _uiState.update { state ->
            val newState = state.copy(
                selectedUnit = unit,
                isUnitDropdownExpanded = false
            )
            val error = validateGoalValue(newState.goalValue, newState.maxGoalValue)
            newState.copy(goalValueError = error)
        }
    }

    fun onUnitDropdownToggle() {
        _uiState.update { state ->
            state.copy(isUnitDropdownExpanded = !state.isUnitDropdownExpanded)
        }
    }

    fun onUnitDropdownDismiss() {
        _uiState.update { state ->
            state.copy(isUnitDropdownExpanded = false)
        }
    }

    fun onRepeatEveryDayChange(repeatEveryDay: Boolean) {
        _uiState.update { state ->
            state.copy(
                repeatEveryDay = repeatEveryDay,
                selectedDays = if (repeatEveryDay) {
                    DayOfWeek.entries.toSet()
                } else {
                    state.selectedDays
                }
            )
        }
    }

    fun onDayToggle(day: DayOfWeek) {
        _uiState.update { state ->
            val newDays = if (state.selectedDays.contains(day)) {
                state.selectedDays - day
            } else {
                state.selectedDays + day
            }
            state.copy(
                selectedDays = newDays,
                repeatEveryDay = newDays.size == DayOfWeek.entries.size
            )
        }
    }

    fun onReminderEnabledChange(enabled: Boolean) {
        _uiState.update { state ->
            state.copy(reminderEnabled = enabled)
        }
    }

    fun onReminderTimeChange(time: LocalTime) {
        _uiState.update { state ->
            state.copy(reminderTime = time)
        }
    }

    fun onSave() {
        viewModelScope.launch {
            val currentState = _uiState.value

            if (!validateForm()) {
                return@launch
            }

            _uiState.update { it.copy(isSaving = true) }

            try {
                val goalValueInSeconds = when {
                    currentState.habitType == HabitType.BOOLEAN -> null
                    currentState.habitType == HabitType.COUNTER -> currentState.goalValue.toIntOrNull()
                    currentState.selectedUnit == MeasurementUnit.HOURS -> {
                        currentState.goalValue.toIntOrNull()?.let { it * 3600 }
                    }
                    currentState.selectedUnit == MeasurementUnit.MINUTES -> {
                        currentState.goalValue.toIntOrNull()?.let { it * 60 }
                    }
                    else -> currentState.goalValue.toIntOrNull()
                }

                val habit = Habit(
                    title = currentState.title.trim(),
                    type = currentState.habitType,
                    goalValue = goalValueInSeconds,
                    unit = currentState.selectedUnit?.displayName,
                    daysOfWeek = currentState.selectedDays,
                    remindEnabled = currentState.reminderEnabled,
                    remindTime = if (currentState.reminderEnabled) currentState.reminderTime else null,
                )

                createHabitUseCase(habit)

                _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isSaving = false) }
            }
        }
    }

    private suspend fun validateForm(): Boolean {
        val currentState = _uiState.value
        var isValid = true

        if (currentState.title.isBlank()) {
            _uiState.update { it.copy(titleError = TitleError.EMPTY) }
            isValid = false
        } else {
            val existingHabits = getActiveHabitsUseCase().first()
            val titleExists = existingHabits.any {
                it.title.equals(currentState.title.trim(), ignoreCase = true)
            }
            if (titleExists) {
                _uiState.update { it.copy(titleError = TitleError.ALREADY_EXISTS) }
                isValid = false
            }
        }

        if (currentState.habitType != HabitType.BOOLEAN && currentState.goalValue.isBlank()) {
            _uiState.update { it.copy(goalValueError = GoalValueError.EMPTY) }
            isValid = false
        }

        val goalError = validateGoalValue(currentState.goalValue, currentState.maxGoalValue)
        if (goalError != null && currentState.habitType != HabitType.BOOLEAN) {
            _uiState.update { it.copy(goalValueError = goalError) }
            isValid = false
        }

        return isValid
    }

    private fun validateGoalValue(value: String, maxValue: Int): GoalValueError? {
        if (value.isBlank()) return null

        val numericValue = value.toIntOrNull() ?: return GoalValueError.INVALID_FORMAT

        if (numericValue > maxValue) return GoalValueError.EXCEEDS_MAX

        return null
    }
}

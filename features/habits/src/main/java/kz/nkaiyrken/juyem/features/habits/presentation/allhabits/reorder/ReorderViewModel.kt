package kz.nkaiyrken.juyem.features.habits.presentation.allhabits.reorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.ArchiveHabitUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.GetActiveHabitsUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.UpdateHabitsOrderUseCase
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.ReorderUiState
import javax.inject.Inject

@HiltViewModel
class ReorderViewModel @Inject constructor(
    getActiveHabitsUseCase: GetActiveHabitsUseCase,
    private val updateHabitsOrderUseCase: UpdateHabitsOrderUseCase,
    private val archiveHabitUseCase: ArchiveHabitUseCase,
) : ViewModel() {

    private val _localHabitsOrder = MutableStateFlow<List<Habit>?>(null)

    val uiState: StateFlow<ReorderUiState> = combine(
        getActiveHabitsUseCase(),
        _localHabitsOrder,
    ) { habits, localOrder ->
        val displayHabits = localOrder ?: habits
        ReorderUiState(
            contentState = when {
                displayHabits.isEmpty() -> ReorderUiState.ContentState.Empty
                else -> ReorderUiState.ContentState.Success(habits = displayHabits)
            }
        )
    }
        .catch { error ->
            emit(
                ReorderUiState(
                    contentState = ReorderUiState.ContentState.Error(
                        message = error.message ?: "Unknown error occurred"
                    )
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReorderUiState()
        )

    fun onMoveHabit(fromIndex: Int, toIndex: Int) {
        val currentState = uiState.value.contentState
        if (currentState !is ReorderUiState.ContentState.Success) return

        val currentList = _localHabitsOrder.value ?: currentState.habits
        val mutableList = currentList.toMutableList()

        val item = mutableList.removeAt(fromIndex)
        mutableList.add(toIndex, item)

        val updatedList = mutableList.mapIndexed { index, habit ->
            habit.copy(orderIndex = index)
        }

        _localHabitsOrder.value = updatedList
    }

    fun onDragEnd() {
        val localOrder = _localHabitsOrder.value ?: return
        viewModelScope.launch {
            updateHabitsOrderUseCase(localOrder)
            _localHabitsOrder.value = null
        }
    }

    fun onArchiveHabit(habitId: Int) {
        viewModelScope.launch {
            archiveHabitUseCase(habitId)
        }
    }
}

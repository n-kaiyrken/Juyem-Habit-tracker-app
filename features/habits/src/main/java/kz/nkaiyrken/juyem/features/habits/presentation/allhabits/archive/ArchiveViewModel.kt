package kz.nkaiyrken.juyem.features.habits.presentation.allhabits.archive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.DeleteHabitUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.GetArchiveHabitsUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.UnarchiveHabitUseCase
import kz.nkaiyrken.juyem.features.habits.presentation.allhabits.ReorderUiState
import javax.inject.Inject

@HiltViewModel
class ArchiveViewModel @Inject constructor(
    getArchiveHabitsUseCase: GetArchiveHabitsUseCase,
    private val unarchiveHabitUseCase: UnarchiveHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
) : ViewModel() {

    val uiState: StateFlow<ReorderUiState> = getArchiveHabitsUseCase()
        .map { habits ->
            ReorderUiState(
                contentState = when {
                    habits.isEmpty() -> ReorderUiState.ContentState.Empty
                    else -> ReorderUiState.ContentState.Success(habits)
                }
            )
        }.catch { error ->
            emit(
                ReorderUiState(
                    contentState = ReorderUiState.ContentState.Error(
                        error.message ?: "Unknown error"
                    )
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ReorderUiState()
        )

    fun deleteAllHabits() {
        val currentState = uiState.value.contentState
        if (currentState !is ReorderUiState.ContentState.Success) return

        viewModelScope.launch {
            currentState.habits.forEach { habit ->
                deleteHabitUseCase(habit.id)
            }
        }
    }

    fun onDeleteHabit(habitId: Int) {
        viewModelScope.launch {
            deleteHabitUseCase(habitId)
        }
    }

    fun onActivateHabit(habitId: Int) {
        viewModelScope.launch {
            unarchiveHabitUseCase(habitId)
        }
    }
}

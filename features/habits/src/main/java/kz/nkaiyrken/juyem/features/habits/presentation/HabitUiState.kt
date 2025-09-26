package kz.nkaiyrken.juyem.features.habits.presentation

sealed interface HabitUiState {
    object Loading : HabitUiState
    data class Error(val throwable: Throwable) : HabitUiState
    data class Success(val data: List<String>) : HabitUiState
}
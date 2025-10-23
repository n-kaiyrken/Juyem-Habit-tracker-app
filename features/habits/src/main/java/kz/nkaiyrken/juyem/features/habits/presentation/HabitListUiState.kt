package kz.nkaiyrken.juyem.features.habits.presentation

sealed interface HabitListUiState {
    object Loading : HabitListUiState
    data class Error(val throwable: Throwable) : HabitListUiState
    data class Success(val data: List<String>) : HabitListUiState
}
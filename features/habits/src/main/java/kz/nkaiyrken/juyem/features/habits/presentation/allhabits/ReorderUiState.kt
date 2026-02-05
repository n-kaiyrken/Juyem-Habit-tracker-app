package kz.nkaiyrken.juyem.features.habits.presentation.allhabits

import kz.nkaiyrken.juyem.core.Habit

data class ReorderUiState(
    val contentState: ContentState = ContentState.Loading,
) {
    sealed interface ContentState {
        data object Loading : ContentState
        data class Success(val habits: List<Habit>) : ContentState
        data class Error(val message: String) : ContentState
        data object Empty : ContentState
    }
}
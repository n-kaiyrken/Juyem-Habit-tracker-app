package kz.nkaiyrken.juyem.features.habits.presentation

import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import java.time.DayOfWeek
import java.time.LocalDate

/**
 * UI State for HabitListScreen
 */
data class HabitListUiState(
    val contentState: ContentState = ContentState.Loading,
    val selectedWeekStartDate: LocalDate = LocalDate.now(),
    val canNavigateToPreviousWeek: Boolean = false,
    val canNavigateToNextWeek: Boolean = false,
    val expandedHabitId: Int? = null,
    val selectedDay: DayOfWeek = LocalDate.now().dayOfWeek,
    val topBarTitle: TopBarTitle = TopBarTitle.ActiveHabits,
) {
    sealed interface ContentState {
        data object Loading : ContentState
        data class Success(val habits: List<HabitCardUiModel>) : ContentState
        data class Error(val message: String, val canRetry: Boolean = true) : ContentState
        data object Empty : ContentState
    }
}
package kz.nkaiyrken.juyem.features.habits.presentation

import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel

/**
 * Sealed interface representing all possible actions that can be performed on a habit card
 */
sealed interface HabitCardAction {
    val dailyProgressUiModel: DailyProgressUiModel

    data class Complete(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction

    data class ConfirmCounter(override val dailyProgressUiModel: DailyProgressUiModel, val count: Int) :
        HabitCardAction

    data class StartTimer(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction

    data class EditNote(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction

    data class Skip(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction

    data class Clear(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction

    data class DayChipClick(override val dailyProgressUiModel: DailyProgressUiModel) : HabitCardAction
}
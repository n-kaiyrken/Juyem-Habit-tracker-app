package kz.nkaiyrken.juyem.features.habits.presentation.models

/**
 * Sealed interface representing all possible actions that can be performed on a habit card
 */
sealed interface HabitAction {
    val dailyProgressUiModel: DailyProgressUiModel

    data class Complete(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction

    data class ConfirmCounter(override val dailyProgressUiModel: DailyProgressUiModel, val count: Int) : HabitAction

    data class StartTimer(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction

    data class EditNote(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction

    data class Skip(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction

    data class Clear(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction

    data class DayChipClick(override val dailyProgressUiModel: DailyProgressUiModel) : HabitAction
}
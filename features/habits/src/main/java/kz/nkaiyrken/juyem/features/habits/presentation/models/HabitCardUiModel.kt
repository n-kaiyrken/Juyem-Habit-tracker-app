package kz.nkaiyrken.juyem.features.habits.presentation.models

import kz.nkaiyrken.juyem.core.HabitType
import java.time.DayOfWeek

data class HabitCardUiModel(
    val habitId: Int,
    val title: String,
    val habitType: HabitType,
    val weekDaysProgress: Map<DayOfWeek, DailyProgressUiModel>,
)


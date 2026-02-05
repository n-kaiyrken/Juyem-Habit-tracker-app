package kz.nkaiyrken.juyem.features.habits.presentation.createhabit

import kz.nkaiyrken.juyem.core.HabitType
import java.time.DayOfWeek
import java.time.LocalTime

data class CreateHabitUiState(
    val title: String = "",
    val titleError: TitleError? = null,
    val habitType: HabitType = HabitType.BOOLEAN,
    val goalValue: String = "",
    val goalValueError: GoalValueError? = null,
    val selectedUnit: MeasurementUnit? = null,
    val selectedDays: Set<DayOfWeek> = DayOfWeek.entries.toSet(),
    val repeatEveryDay: Boolean = true,
    val reminderEnabled: Boolean = false,
    val reminderTime: LocalTime = LocalTime.of(7, 0),
    val isUnitDropdownExpanded: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
) {
    val isFormValid: Boolean
        get() = title.isNotBlank() &&
                titleError == null &&
                goalValueError == null &&
                (habitType == HabitType.BOOLEAN || goalValue.isNotBlank()) &&
                selectedDays.isNotEmpty()

    val maxGoalValue: Int
        get() = when {
            habitType == HabitType.TIMER && selectedUnit == MeasurementUnit.HOURS -> MAX_HOURS_PER_DAY
            habitType == HabitType.TIMER -> MAX_MINUTES_PER_DAY
            else -> MAX_COUNTER_VALUE
        }

    companion object {
        const val MAX_MINUTES_PER_DAY = 1440
        const val MAX_HOURS_PER_DAY = 24
        const val MAX_COUNTER_VALUE = 999999
    }
}

enum class TitleError {
    EMPTY,
    ALREADY_EXISTS
}

enum class GoalValueError {
    EMPTY,
    EXCEEDS_MAX,
    INVALID_FORMAT
}

enum class MeasurementUnit(val displayName: String) {
    METERS("м"),
    KILOMETERS("км"),
    MINUTES("минут"),
    HOURS("часов"),
    TIMES("раз"),
    GLASSES("стаканов"),
    PAGES("страниц"),
}

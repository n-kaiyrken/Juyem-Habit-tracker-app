package kz.nkaiyrken.juyem.core.ui.widgets.chip

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

/**
 * Get abbreviated day of week label in Russian.
 *
 * @param dayOfWeek Day of week
 * @return Abbreviated label (e.g., "Пн", "Вт", "Ср")
 */
fun getDayLabel(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "пн"
        DayOfWeek.TUESDAY -> "вт"
        DayOfWeek.WEDNESDAY -> "ср"
        DayOfWeek.THURSDAY -> "чт"
        DayOfWeek.FRIDAY -> "пт"
        DayOfWeek.SATURDAY -> "сб"
        DayOfWeek.SUNDAY -> "вс"
    }
}

/**
 * Get full day of week name in Russian.
 *
 * @param dayOfWeek Day of week
 * @return Full name (e.g., "Понедельник", "Вторник")
 */
fun getDayFullName(dayOfWeek: DayOfWeek): String {
    return dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))
}

/**
 * Get abbreviated day labels for the entire week.
 *
 * @return List of 7 labels starting from Monday
 */
fun getWeekLabels(): List<String> {
    return DayOfWeek.values().map { getDayLabel(it) }
}

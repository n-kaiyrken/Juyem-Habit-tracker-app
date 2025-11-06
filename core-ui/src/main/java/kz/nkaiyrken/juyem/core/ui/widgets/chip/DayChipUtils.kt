package kz.nkaiyrken.juyem.core.ui.widgets.chip

import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

fun getDayLabel(dayOfWeek: DayOfWeek): String {
    return when (dayOfWeek) {
        DayOfWeek.MONDAY -> "ПН"
        DayOfWeek.TUESDAY -> "ВТ"
        DayOfWeek.WEDNESDAY -> "СР"
        DayOfWeek.THURSDAY -> "ЧТ"
        DayOfWeek.FRIDAY -> "ПТ"
        DayOfWeek.SATURDAY -> "СБ"
        DayOfWeek.SUNDAY -> "ВС"
    }
}

fun getDayFullName(dayOfWeek: DayOfWeek): String {
    return dayOfWeek.getDisplayName(TextStyle.FULL, Locale("ru"))
}

fun getWeekLabels(): List<String> {
    return DayOfWeek.entries.map { getDayLabel(it) }
}

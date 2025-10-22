package kz.nkaiyrken.juyem.core.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

object DateUtils {

    fun getDateRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val days = ChronoUnit.DAYS.between(startDate, endDate).toInt()
        return (0..days).map { startDate.plusDays(it.toLong()) }
    }

    fun getWeekStart(date: LocalDate): LocalDate {
        return date.minusDays((date.dayOfWeek.value - 1).toLong())
    }

    fun getWeekEnd(date: LocalDate): LocalDate {
        return date.plusDays((7 - date.dayOfWeek.value).toLong())
    }

    fun getMonthStart(date: LocalDate): LocalDate {
        return date.withDayOfMonth(1)
    }

    fun getMonthEnd(date: LocalDate): LocalDate {
        return date.withDayOfMonth(date.lengthOfMonth())
    }

    fun isToday(date: LocalDate): Boolean {
        return date == LocalDate.now()
    }

    fun isYesterday(date: LocalDate): Boolean {
        return date == LocalDate.now().minusDays(1)
    }

    fun daysBetween(start: LocalDate, end: LocalDate): Int {
        return ChronoUnit.DAYS.between(start, end).toInt()
    }
}
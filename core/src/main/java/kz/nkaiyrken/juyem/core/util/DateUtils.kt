package kz.nkaiyrken.juyem.core.util

import kz.nkaiyrken.juyem.core.util.DateUtils.getCurrentWeekDates
import java.time.DayOfWeek
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

    fun daysBetween(start: LocalDate, end: LocalDate): Int {
        return ChronoUnit.DAYS.between(start, end).toInt()
    }

    fun getCurrentWeekDates(): List<LocalDate> {
        val weekStart = getWeekStart(LocalDate.now())
        return getDateRange(weekStart, weekStart.plusDays(6))
    }

    fun getWeekDates(date: LocalDate): List<LocalDate> {
        val weekStart = getWeekStart(date)
        return getDateRange(weekStart, weekStart.plusDays(6))
    }

    fun getDateByDayOfWeek(dayOfWeek: DayOfWeek, weekStartDate: LocalDate): LocalDate {
        return weekStartDate.plusDays((dayOfWeek.value - 1).toLong())
    }
}

fun LocalDate.isPast(): Boolean = this.isBefore(LocalDate.now())

fun LocalDate.isFuture(): Boolean = this.isAfter(LocalDate.now())

fun LocalDate.isCurrentWeek(): Boolean = getCurrentWeekDates().contains(this)

fun LocalDate.isToday(): Boolean = this == LocalDate.now()

fun LocalDate.isYesterday() = this == LocalDate.now().minusDays(1)

fun LocalDate.isTomorrow() = this == LocalDate.now().plusDays(1)

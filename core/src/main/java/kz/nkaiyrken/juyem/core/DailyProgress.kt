package kz.nkaiyrken.juyem.core

import java.time.LocalDate

/**
 * Represents daily progress for a habit.
 *
 * @property wasScheduled Indicates if this day was in habit's daysOfWeek schedule when record was created.
 *                        Used to handle schedule changes without losing historical data.
 *
 * Examples:
 * - User completes habit on Saturday with daysOfWeek=[Mon-Sun], wasScheduled=true
 * - User later removes Saturday from schedule: daysOfWeek=[Mon-Fri]
 * - Old Saturday record still shows COMPLETED status and is clickable (for editing)
 * - But new Saturdays show as EMPTY and disabled (not in current schedule)
 */
data class DailyProgress(
    val habitId: Int,
    val date: LocalDate,
    val value: Int = 0,
    val status: DailyProgressStatus = DailyProgressStatus.EMPTY,
    val wasScheduled: Boolean = true
)

enum class DailyProgressStatus {
    COMPLETED,  // Выполнено полностью
    PARTIAL,    // Частично выполнено
    SKIPPED,    // Пропущено
    EMPTY,      // Пусто
    FAILED      // Не выполнено
}
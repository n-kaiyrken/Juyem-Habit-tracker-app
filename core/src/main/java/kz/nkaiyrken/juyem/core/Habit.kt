package kz.nkaiyrken.juyem.core

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class Habit(
    val id: Int = 0,
    val title: String,
    val orderIndex: Int? = null,
    val type: HabitType,
    val goalValue: Int? = null,
    val unit: String? = null,
    val daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet(),
    val remindEnabled: Boolean = false,
    val remindTime: LocalTime? = null,
    val status: HabitStatus = HabitStatus.ACTIVE,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val archivedAt: LocalDateTime? = null
) {
    val isScheduledForToday: Boolean
        get() = daysOfWeek.contains(LocalDate.now().dayOfWeek)

}

enum class HabitType {
    BOOLEAN,   // Да/Нет (выполнено/не выполнено)
    COUNTER,   // Счетчик (количество раз)
    TIMER      // Таймер (время в секундах)
}

enum class HabitStatus {
    ACTIVE,
    PAUSED,
    ARCHIVED
}
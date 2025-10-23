package kz.nkaiyrken.juyem.core

import java.time.LocalDate

data class DailyProgress(
    val habitId: Int,
    val date: LocalDate,
    val value: Int = 0,
    val status: DailyProgressStatus = DailyProgressStatus.EMPTY
)

enum class DailyProgressStatus {
    COMPLETED,  // Выполнено полностью
    PARTIAL,    // Частично выполнено
    SKIPPED,    // Пропущено
    EMPTY,      // Пусто
    FAILED      // Не выполнено
}
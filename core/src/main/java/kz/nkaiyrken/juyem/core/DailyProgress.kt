package kz.nkaiyrken.juyem.core

import java.time.LocalDate

data class DailyProgress(
    val habitId: Int,
    val date: LocalDate,
    val value: Int = 0,
    val status: DailyProgressStatus = DailyProgressStatus.EMPTY,
    val wasScheduled: Boolean = true
)

enum class DailyProgressStatus {
    COMPLETED,
    PARTIAL,
    SKIPPED,
    EMPTY,
    FAILED
}
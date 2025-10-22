package kz.nkaiyrken.juyem.core

import java.time.LocalDate
import java.time.LocalDateTime

data class TimerSession(
    val id: Int = 0,
    val habitId: Int,
    val date: LocalDate,
    val startedAt: LocalDateTime,
    val pausedAt: LocalDateTime? = null,
    val elapsedSeconds: Int = 0,
    val state: TimerState
)

enum class TimerState {
    RUNNING,
    PAUSED,
    COMPLETED
}
package kz.nkaiyrken.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "timer_sessions",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ],
    indices = [Index(value = ["habit_id"])]
)
data class TimerSessionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_id")
    val sessionId: Int = 0,

    @ColumnInfo(name = "habit_id")
    val habitId: Int,

    @ColumnInfo(name = "date")
    val date: LocalDate,

    @ColumnInfo(name = "started_at")
    val startedAt: LocalDateTime,

    @ColumnInfo(name = "paused_at")
    val pausedAt: LocalDateTime? = null,

    @ColumnInfo(name = "elapsed_seconds")
    val elapsedSeconds: Int = 0,

    @ColumnInfo(name = "state")
    val state: String // "running", "paused", "completed"
)
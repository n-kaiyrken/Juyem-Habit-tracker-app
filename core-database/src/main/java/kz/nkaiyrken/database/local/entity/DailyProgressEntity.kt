package kz.nkaiyrken.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.time.LocalDate

@Entity(
    tableName = "daily_progress",
    primaryKeys = ["habit_id", "date"],
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["habit_id", "date"])]
)
data class DailyProgressEntity(
    @ColumnInfo(name = "habit_id")
    val habitId: Int,

    @ColumnInfo(name = "date")
    val date: LocalDate,

    @ColumnInfo(name = "value")
    val value: Int = 0,

    @ColumnInfo(name = "status")
    val status: String // "completed", "skipped", "failed", "partial"
)


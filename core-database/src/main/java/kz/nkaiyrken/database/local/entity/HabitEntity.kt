package kz.nkaiyrken.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id")
    val habitId: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "order_index")
    val orderIndex: Int? = null,

    @ColumnInfo(name = "type")
    val type: String, // "boolean", "counter", "timer"

    @ColumnInfo(name = "goal_value")
    val goalValue: Int? = null,

    @ColumnInfo(name = "unit")
    val unit: String? = null,

    @ColumnInfo(name = "days_of_week")
    val daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet(),

    @ColumnInfo(name = "remind_enabled")
    val remindEnabled: Boolean = false,

    @ColumnInfo(name = "remind_time")
    val remindTime: LocalTime? = null,

    @ColumnInfo(name = "status")
    val status: String = "active", // "active", "paused", "archived"

    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @ColumnInfo(name = "archived_at")
    val archivedAt: LocalDateTime? = null
)
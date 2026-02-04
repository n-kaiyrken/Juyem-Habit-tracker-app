package kz.nkaiyrken.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "statistics",
    foreignKeys = [
        ForeignKey(
            entity = HabitEntity::class,
            parentColumns = ["habit_id"],
            childColumns = ["habit_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class StatisticsEntity(
    @PrimaryKey
    @ColumnInfo(name = "habit_id")
    val habitId: Int,

    @ColumnInfo(name = "streak_current")
    val streakCurrent: Int = 0,

    @ColumnInfo(name = "streak_max")
    val streakMax: Int = 0,

    @ColumnInfo(name = "completed_total")
    val completedTotal: Int = 0,

    @ColumnInfo(name = "skipped_total")
    val skippedTotal: Int = 0,

    @ColumnInfo(name = "failed_total")
    val failedTotal: Int = 0,

    @ColumnInfo(name = "max_value")
    val maxValue: Int? = null,

    @ColumnInfo(name = "min_value")
    val minValue: Int? = null,

    @ColumnInfo(name = "total_value")
    val totalValue: Int = 0
)
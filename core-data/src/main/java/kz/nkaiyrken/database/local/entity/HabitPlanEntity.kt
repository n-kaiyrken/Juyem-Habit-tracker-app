package kz.nkaiyrken.database.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "habit_plans",
    primaryKeys = ["habit_id", "period"],
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
data class HabitPlanEntity(
    @ColumnInfo(name = "habit_id")
    val habitId: Int,

    @ColumnInfo(name = "period")
    val period: String, // "weekly", "monthly", "yearly"

    @ColumnInfo(name = "target_value")
    val targetValue: Int,

    @ColumnInfo(name = "achieved_value")
    val achievedValue: Int = 0
)
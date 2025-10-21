package kz.nkaiyrken.database.local.model

import androidx.room.Embedded
import androidx.room.Relation
import kz.nkaiyrken.database.local.entity.DailyProgressEntity
import kz.nkaiyrken.database.local.entity.HabitEntity
import kz.nkaiyrken.database.local.entity.HabitPlanEntity
import kz.nkaiyrken.database.local.entity.StatisticsEntity

/**
 * DTO для экрана деталей привычки.
 * Объединяет все необходимые данные в одну модель:
 * - Основная информация о привычке
 * - Статистика
 * - Планы (неделя, месяц, год)
 */
data class HabitWithDetails(
    @Embedded
    val habit: HabitEntity,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_id"
    )
    val statistics: StatisticsEntity?,

    @Relation(
        parentColumn = "habit_id",
        entityColumn = "habit_id"
    )
    val plans: List<HabitPlanEntity>
)

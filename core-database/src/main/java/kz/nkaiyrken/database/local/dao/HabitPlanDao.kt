package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.MapColumn
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.HabitPlanEntity

@Dao
interface HabitPlanDao: BaseDao<HabitPlanEntity> {
    @Query("SELECT * FROM habit_plans WHERE habit_id = :habitId")
    fun getPlans(habitId: Int): Flow<List<HabitPlanEntity>>

    @Query("SELECT * FROM habit_plans WHERE habit_id = :habitId AND period = :period")
    fun getPlanByPeriod(habitId: Int, period: String): Flow<HabitPlanEntity?>

    @Query("SELECT period, target_value FROM habit_plans WHERE habit_id = :habitId")
    fun getPlansAsMap(habitId: Int): Flow<Map<@MapColumn("period") String, @MapColumn("target_value") Int>>
}
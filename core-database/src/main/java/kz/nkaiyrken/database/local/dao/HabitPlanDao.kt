package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.MapInfo
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.HabitPlanEntity

@Dao
interface HabitPlanDao {
    @Query("SELECT * FROM habit_plans WHERE habit_id = :habitId")
    fun getPlans(habitId: Int): Flow<List<HabitPlanEntity>>

    @Query("SELECT * FROM habit_plans WHERE habit_id = :habitId AND period = :period")
    fun getPlanByPeriod(habitId: Int, period: String): Flow<HabitPlanEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPlan(plan: HabitPlanEntity)

    @Update
    suspend fun updatePlan(plan: HabitPlanEntity)

    @Delete
    suspend fun deletePlan(plan: HabitPlanEntity)

    @Query("SELECT period, target_value FROM habit_plans WHERE habit_id = :habitId")
    fun getPlansAsMap(habitId: Int): Flow<Map<@MapColumn("period") String, @MapColumn("target_value") Int>>
}
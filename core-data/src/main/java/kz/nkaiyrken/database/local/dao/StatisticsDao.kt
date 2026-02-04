package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.StatisticsEntity

@Dao
interface StatisticsDao: BaseDao<StatisticsEntity> {
    @Query("SELECT * FROM statistics WHERE habit_id = :habitId")
    fun getStatistics(habitId: Int): Flow<StatisticsEntity?>

    @Query("UPDATE statistics SET streak_current = :current, streak_max = :max WHERE habit_id = :habitId")
    suspend fun updateStreak(habitId: Int, current: Int, max: Int)
}
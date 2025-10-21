package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.StatisticsEntity

@Dao
interface StatisticsDao {
    @Query("SELECT * FROM statistics WHERE habit_id = :habitId")
    fun getStatistics(habitId: Int): Flow<StatisticsEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertStatistics(statistics: StatisticsEntity)

    @Update
    suspend fun updateStatistics(statistics: StatisticsEntity)

    @Query("UPDATE statistics SET streak_current = :current, streak_max = :max WHERE habit_id = :habitId")
    suspend fun updateStreak(habitId: Int, current: Int, max: Int)
}
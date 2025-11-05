package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.HabitEntity
import kz.nkaiyrken.database.local.model.HabitWithDetails

@Dao
interface HabitDao: BaseDao<HabitEntity> {
    @Query("SELECT * FROM habits WHERE status = 'ACTIVE' ORDER BY order_index ASC")
    fun getActiveHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM habits WHERE habit_id = :habitId")
    fun getHabitById(habitId: Int): Flow<HabitEntity?>

    @Transaction
    @Query("SELECT * FROM habits WHERE habit_id = :habitId")
    fun getHabitWithDetails(habitId: Int): Flow<HabitWithDetails?>

    @Query("SELECT * FROM habits WHERE status = 'archived'")
    fun getArchivedHabits(): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET status = 'archived', archived_at = :archivedAt WHERE habit_id = :habitId")
    suspend fun archiveHabit(habitId: Int, archivedAt: java.time.LocalDateTime)

    @Query("UPDATE habits SET status = 'active', archived_at = NULL WHERE habit_id = :habitId")
    suspend fun unarchiveHabit(habitId: Int)

    @Query("DELETE FROM habits WHERE habit_id = :habitId")
    suspend fun deleteById(habitId: Int)
}


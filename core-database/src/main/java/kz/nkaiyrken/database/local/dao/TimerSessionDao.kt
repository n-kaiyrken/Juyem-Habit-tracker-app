package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.TimerSessionEntity
import java.time.LocalDate

@Dao
interface TimerSessionDao {
    @Query("SELECT * FROM timer_sessions WHERE habit_id = :habitId AND state != 'completed' ORDER BY started_at DESC LIMIT 1")
    fun getActiveSession(habitId: Int): Flow<TimerSessionEntity?>

    @Query("SELECT * FROM timer_sessions WHERE habit_id = :habitId AND date = :date")
    fun getSessionsForDate(habitId: Int, date: LocalDate): Flow<List<TimerSessionEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertSession(session: TimerSessionEntity): Long

    @Update
    suspend fun updateSession(session: TimerSessionEntity)

    @Delete
    suspend fun deleteSession(session: TimerSessionEntity)
}
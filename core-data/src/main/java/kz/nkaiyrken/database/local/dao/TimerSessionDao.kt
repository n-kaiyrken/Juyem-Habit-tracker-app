package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.TimerSessionEntity
import java.time.LocalDate

@Dao
interface TimerSessionDao: BaseDao<TimerSessionEntity> {
    @Query("SELECT * FROM timer_sessions WHERE habit_id = :habitId AND state != 'completed' ORDER BY started_at DESC LIMIT 1")
    fun getActiveSession(habitId: Int): Flow<TimerSessionEntity?>

    @Query("SELECT * FROM timer_sessions WHERE habit_id = :habitId AND date = :date")
    fun getSessionsForDate(habitId: Int, date: LocalDate): Flow<List<TimerSessionEntity>>
}
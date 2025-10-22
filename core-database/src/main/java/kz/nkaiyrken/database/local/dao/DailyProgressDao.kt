package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.DailyProgressEntity
import java.time.LocalDate

@Dao
interface DailyProgressDao: BaseDao<DailyProgressEntity> {
    @Query("SELECT * FROM daily_progress WHERE habit_id = :habitId AND date = :date")
    fun getProgress(habitId: Int, date: LocalDate): Flow<DailyProgressEntity?>

    @Query("SELECT * FROM daily_progress WHERE habit_id = :habitId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getProgressRange(
        habitId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Flow<List<DailyProgressEntity>>

    @Query("SELECT * FROM daily_progress WHERE date = :date")
    fun getAllProgressForDate(date: LocalDate): Flow<List<DailyProgressEntity>>

    @Query("DELETE FROM daily_progress WHERE habit_id = :habitId AND date < :date")
    suspend fun deleteOldProgress(habitId: Int, date: LocalDate)

    // Для календаря: получаем date и status для отображения цветовой индикации
    @Query(
        """
        SELECT date, status FROM daily_progress
        WHERE habit_id = :habitId
        AND strftime('%Y-%m', date) = :yearMonth
        ORDER BY date ASC
    """
    )
    fun getProgressForMonth(habitId: Int, yearMonth: String): Flow<List<CalendarDayData?>>

    // Для истории: получаем date и value для горизонтального скролла дней месяца
    @Query(
        """
        SELECT date, value FROM daily_progress
        WHERE habit_id = :habitId
        AND strftime('%Y-%m', date) = :yearMonth
        ORDER BY date ASC
    """
    )
    fun getMonthlyHistory(habitId: Int, yearMonth: String): Flow<List<HistoryDayData>>

    // DTO для календаря - только date и status
    data class CalendarDayData(
        val date: LocalDate,
        val status: String,
    )

    // DTO для истории - только date и value
    data class HistoryDayData(
        val date: LocalDate,
        val value: Int,
    )
}
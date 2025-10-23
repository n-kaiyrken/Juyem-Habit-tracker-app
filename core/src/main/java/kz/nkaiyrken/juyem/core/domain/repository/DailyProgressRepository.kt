package kz.nkaiyrken.juyem.core.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.DailyProgress
import java.time.LocalDate

/**
 * Repository интерфейс для работы с ежедневным прогрессом привычек.
 */
interface DailyProgressRepository {

    /**
     * Получить прогресс привычки за конкретный день.
     *
     * @param habitId идентификатор привычки
     * @param date дата
     * @return Flow с прогрессом или null если данных нет
     */
    fun getDailyProgress(habitId: Int, date: LocalDate): Flow<DailyProgress?>

    /**
     * Получить прогресс привычки за неделю (7 дней начиная с startDate).
     * Используется для отображения недельного view в HabitListScreen.
     *
     * @param habitId идентификатор привычки
     * @param startDate начало недели (обычно понедельник)
     * @return Flow со списком прогресса (может быть меньше 7 элементов если данных нет)
     */
    fun getWeeklyProgress(habitId: Int, startDate: LocalDate): Flow<List<DailyProgress>>

    /**
     * Получить прогресс всех привычек за конкретный день.
     * Используется для быстрой загрузки всех данных за день.
     *
     * @param date дата
     * @return Flow с картой habitId -> DailyProgress
     */
    fun getAllProgressForDate(date: LocalDate): Flow<Map<Int, DailyProgress>>

    /**
     * Получить прогресс всех привычек за диапазон дат.
     * Используется для недельного view всех привычек.
     *
     * @param startDate начальная дата
     * @param endDate конечная дата
     * @return Flow с картой habitId -> Map(date -> DailyProgress)
     */
    fun getProgressForDateRange(
        startDate: LocalDate,
        endDate: LocalDate
    ): Flow<Map<Int, Map<LocalDate, DailyProgress>>>

    /**
     * Сохранить или обновить прогресс за день (UPSERT операция).
     * Если запись существует - обновляется, если нет - создается.
     *
     * @param progress данные прогресса
     */
    suspend fun upsertProgress(progress: DailyProgress)

    /**
     * Удалить прогресс за конкретный день.
     *
     * @param habitId идентификатор привычки
     * @param date дата
     */
    suspend fun deleteProgress(habitId: Int, date: LocalDate)

    /**
     * Удалить весь прогресс привычки.
     * Обычно вызывается автоматически при удалении привычки (CASCADE).
     *
     * @param habitId идентификатор привычки
     */
    suspend fun deleteAllProgressForHabit(habitId: Int)
}

package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.util.DateUtils
import java.time.LocalDate

/**
 * Use Case для получения прогресса всех привычек за неделю.
 *
 * Бизнес-логика:
 * - Вычисляет начало текущей недели (понедельник)
 * - Загружает прогресс всех привычек за 7 дней (пн-вс)
 * - Возвращает Map структуру для удобства UI: habitId -> (date -> progress)
 *
 * Используется в:
 * - HabitListScreen - недельный view всех привычек
 *
 * @param repository источник данных о прогрессе
 */
class GetWeeklyProgressUseCase(
    private val repository: kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
) {
    /**
     * Получить прогресс всех привычек за текущую неделю.
     *
     * @return Flow с Map<HabitId, Map<Date, DailyProgress>>
     *         Внешний Map: habitId -> прогресс этой привычки
     *         Внутренний Map: date -> прогресс за эту дату
     */
    operator fun invoke(): Flow<Map<Int, Map<LocalDate, DailyProgress>>> {
        return invoke(LocalDate.now())
    }

    /**
     * Получить прогресс всех привычек за неделю, содержащую указанную дату.
     *
     * @param date дата внутри нужной недели
     * @return Flow с прогрессом за всю неделю
     */
    operator fun invoke(date: LocalDate): Flow<Map<Int, Map<LocalDate, DailyProgress>>> {
        val weekStart = DateUtils.getWeekStart(date)
        val weekEnd = DateUtils.getWeekEnd(date)

        return repository.getProgressForDateRange(weekStart, weekEnd)
    }

    /**
     * Получить список дат текущей недели (7 дней: пн-вс).
     * Используется UI для отображения заголовков дней.
     *
     * @return список из 7 дат начиная с понедельника
     */
    fun getCurrentWeekDates(): List<LocalDate> {
        val weekStart = DateUtils.getWeekStart(LocalDate.now())
        return DateUtils.getDateRange(weekStart, weekStart.plusDays(6))
    }

    /**
     * Получить список дат для недели содержащей указанную дату.
     *
     * @param date дата внутри нужной недели
     * @return список из 7 дат
     */
    fun getWeekDates(date: LocalDate): List<LocalDate> {
        val weekStart = DateUtils.getWeekStart(date)
        return DateUtils.getDateRange(weekStart, weekStart.plusDays(6))
    }
}

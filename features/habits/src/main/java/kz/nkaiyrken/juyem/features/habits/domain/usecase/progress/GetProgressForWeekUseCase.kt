package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.features.habits.domain.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.util.DateUtils
import java.time.LocalDate
import javax.inject.Inject

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
class GetProgressForWeekUseCase @Inject constructor (
    private val repository: DailyProgressRepository
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
}

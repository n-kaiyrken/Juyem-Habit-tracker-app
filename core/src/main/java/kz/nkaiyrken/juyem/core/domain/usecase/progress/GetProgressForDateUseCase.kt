package kz.nkaiyrken.juyem.core.domain.usecase.progress

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository
import java.time.LocalDate

/**
 * Use Case для получения прогресса привычки за конкретный день.
 *
 * Атомарная операция - получение одной записи.
 *
 * Используется:
 * - ViewModel для проверки текущего состояния
 * - Композиция в других Use Cases
 * - UI для отображения деталей дня
 *
 * @param progressRepository источник данных о прогрессе
 */
class GetProgressForDateUseCase(
    private val progressRepository: DailyProgressRepository
) {
    /**
     * Получить прогресс за конкретный день.
     * Возвращает null если данных нет.
     *
     * @param habitId идентификатор привычки
     * @param date дата
     * @return Flow с прогрессом или null
     */
    operator fun invoke(habitId: Int, date: LocalDate): Flow<DailyProgress?> {
        return progressRepository.getDailyProgress(habitId, date)
    }
}

package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository

/**
 * Use Case для получения привычки по ID.
 *
 * Атомарная операция - получение одной привычки.
 *
 * Используется:
 * - HabitDetailScreen - детальный экран привычки
 * - EditHabitScreen - экран редактирования
 * - ViewModel для композиции (проверка типа привычки и т.д.)
 *
 * @param repository источник данных о привычках
 */
class GetHabitByIdUseCase(
    private val repository: HabitRepository
) {
    /**
     * Получить привычку по ID.
     * Возвращает null если привычка не найдена.
     *
     * @param id уникальный идентификатор привычки
     * @return Flow с привычкой или null
     */
    operator fun invoke(id: Int): Flow<Habit?> {
        return repository.getHabitById(id)
    }
}

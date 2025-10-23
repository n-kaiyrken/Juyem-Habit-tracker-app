package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository

/**
 * Use Case для получения списка активных привычек.
 *
 * Бизнес-логика:
 * - Получает все привычки со статусом ACTIVE
 * - Привычки уже отсортированы по orderIndex (в Repository/DAO)
 *
 * Используется в:
 * - HabitListScreen - основной экран со списком
 * - HomeWidget - виджет на главном экране (будущее)
 *
 * @param repository источник данных о привычках
 */
class GetActiveHabitsUseCase(
    private val repository: HabitRepository
) {
    /**
     * Получить активные привычки.
     *
     * @return Flow со списком активных привычек, обновляется при изменениях в БД
     */
    operator fun invoke(): Flow<List<Habit>> {
        return repository.getActiveHabits()
    }
}

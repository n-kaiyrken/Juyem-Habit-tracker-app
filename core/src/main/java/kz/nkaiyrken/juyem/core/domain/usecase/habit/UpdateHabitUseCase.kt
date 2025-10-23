package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository

/**
 * Use Case для обновления существующей привычки.
 *
 * Атомарная операция - обновление в БД.
 *
 * Используется:
 * - EditHabitScreen - экран редактирования привычки
 * - Любые места где нужно изменить параметры привычки
 *
 * @param repository источник данных о привычках
 */
class UpdateHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Обновить существующую привычку.
     *
     * @param habit привычка с обновленными данными (id должен существовать)
     */
    suspend operator fun invoke(habit: Habit) {
        repository.updateHabit(habit)
    }
}

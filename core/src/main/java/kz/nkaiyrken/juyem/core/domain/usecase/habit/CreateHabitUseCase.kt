package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository

/**
 * Use Case для создания новой привычки.
 *
 * Атомарная операция - вставка в БД.
 *
 * Используется:
 * - CreateHabitScreen - экран создания привычки
 * - Любые места где нужно добавить привычку
 *
 * @param repository источник данных о привычках
 */
class CreateHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Создать новую привычку.
     *
     * @param habit новая привычка (id = 0, будет сгенерирован автоматически)
     * @return ID созданной привычки
     */
    suspend operator fun invoke(habit: Habit): Long {
        return repository.insertHabit(habit)
    }
}

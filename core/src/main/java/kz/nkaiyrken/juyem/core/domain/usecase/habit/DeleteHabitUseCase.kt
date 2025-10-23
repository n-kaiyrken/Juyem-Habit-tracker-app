package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository

/**
 * Use Case для удаления привычки.
 *
 * Атомарная операция - удаление из БД.
 *
 * ВАЖНО: Удаление привычки автоматически удаляет:
 * - Весь прогресс (DailyProgress) - CASCADE
 * - Статистику (Statistics) - CASCADE
 * - Планы (HabitPlan) - CASCADE
 * - Заметки (Note) - CASCADE
 * - Сессии таймера (TimerSession) - CASCADE
 *
 * Используется:
 * - HabitDetailScreen - кнопка удаления
 * - EditHabitScreen - кнопка удаления
 * - Любые места где нужно удалить привычку
 *
 * @param repository источник данных о привычках
 */
class DeleteHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Удалить привычку по ID.
     * Удаляет также все связанные данные благодаря CASCADE в БД.
     *
     * @param id идентификатор привычки
     */
    suspend operator fun invoke(id: Int) {
        repository.deleteHabit(id)
    }
}

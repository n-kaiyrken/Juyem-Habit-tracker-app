package kz.nkaiyrken.juyem.core.domain.usecase.habit

import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository
import java.time.LocalDateTime

/**
 * Use Case для архивации привычки.
 *
 * Атомарная операция - изменение статуса на ARCHIVED.
 *
 * Архивация vs Удаление:
 * - Архивация: данные сохраняются, привычка скрыта, можно восстановить
 * - Удаление: все данные удаляются безвозвратно
 *
 * Используется:
 * - HabitDetailScreen - кнопка архивации
 * - EditHabitScreen - кнопка архивации
 * - Когда пользователь хочет "спрятать" привычку но не удалять историю
 *
 * @param repository источник данных о привычках
 */
class ArchiveHabitUseCase(
    private val repository: HabitRepository
) {
    /**
     * Архивировать привычку (перевести в статус ARCHIVED).
     * Привычка не будет отображаться в основном списке.
     *
     * @param id идентификатор привычки
     * @param archivedAt время архивации (по умолчанию текущее)
     */
    suspend operator fun invoke(id: Int, archivedAt: LocalDateTime = LocalDateTime.now()) {
        repository.archiveHabit(id, archivedAt)
    }

    /**
     * Восстановить привычку из архива (перевести в статус ACTIVE).
     *
     * @param id идентификатор привычки
     */
    suspend fun unarchive(id: Int) {
        repository.unarchiveHabit(id)
    }
}

package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

/**
 * ВАЖНО: Удаление привычки автоматически удаляет:
 * - Весь прогресс (DailyProgress) - CASCADE
 * - Статистику (Statistics) - CASCADE
 * - Планы (HabitPlan) - CASCADE
 * - Заметки (Note) - CASCADE
 * - Сессии таймера (TimerSession) - CASCADE
 */
class DeleteHabitUseCase(
    private val repository: kz.nkaiyrken.juyem.core.data.repository.HabitRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteHabit(id)
    }
}

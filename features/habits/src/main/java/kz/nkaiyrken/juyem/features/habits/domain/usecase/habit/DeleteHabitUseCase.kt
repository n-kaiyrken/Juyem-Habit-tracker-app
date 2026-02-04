package kz.nkaiyrken.juyem.features.habits.domain.usecase.habit

import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import javax.inject.Inject

/**
 * ВАЖНО: Удаление привычки автоматически удаляет:
 * - Весь прогресс (DailyProgress) - CASCADE
 * - Статистику (Statistics) - CASCADE
 * - Планы (HabitPlan) - CASCADE
 * - Заметки (Note) - CASCADE
 * - Сессии таймера (TimerSession) - CASCADE
 */
class DeleteHabitUseCase @Inject constructor(
    private val repository: HabitRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.deleteHabit(id)
    }
}

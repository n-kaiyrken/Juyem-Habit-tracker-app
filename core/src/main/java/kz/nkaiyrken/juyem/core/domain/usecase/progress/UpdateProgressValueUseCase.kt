package kz.nkaiyrken.juyem.core.domain.usecase.progress

import kotlinx.coroutines.flow.first
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository
import java.time.LocalDate

/**
 * Use Case для обновления ТОЛЬКО значения прогресса (value).
 *
 * Атомарная операция - не меняет status, только value.
 * Подходит для COUNTER и TIMER привычек.
 *
 * Используется:
 * - Пользователь вводит значение в поле COUNTER
 * - Обновление времени в TIMER
 * - Increment/Decrement через ViewModel композицию
 *
 * @param progressRepository источник данных о прогрессе
 */
class UpdateProgressValueUseCase(
    private val progressRepository: DailyProgressRepository
) {
    /**
     * Обновить значение прогресса за день.
     *
     * @param habitId идентификатор привычки
     * @param date дата
     * @param value новое значение (для COUNTER/TIMER)
     */
    suspend operator fun invoke(
        habitId: Int,
        date: LocalDate,
        value: Int
    ) {
        val currentProgress = progressRepository.getDailyProgress(habitId, date).first()

        val updated = currentProgress?.copy(value = value)
            ?: DailyProgress(
                habitId = habitId,
                date = date,
                value = value,
                status = DailyProgressStatus.EMPTY
            )

        progressRepository.upsertProgress(updated)
    }
}

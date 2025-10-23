package kz.nkaiyrken.juyem.core.domain.usecase.progress

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository

/**
 * Use Case для полного обновления прогресса (и status и value одновременно).
 *
 * Атомарная операция - обновляет весь объект DailyProgress целиком.
 *
 * Используется:
 * - Когда нужно обновить и статус и значение одновременно
 * - Complete для COUNTER/TIMER (value = goalValue, status = COMPLETED)
 * - Любые комплексные обновления из ViewModel
 *
 * @param progressRepository источник данных о прогрессе
 */
class UpsertDailyProgressUseCase(
    private val progressRepository: DailyProgressRepository
) {
    /**
     * Создать или обновить прогресс за день.
     * UPSERT = INSERT если не существует, UPDATE если существует.
     *
     * @param progress полный объект прогресса
     */
    suspend operator fun invoke(progress: DailyProgress) {
        progressRepository.upsertProgress(progress)
    }
}

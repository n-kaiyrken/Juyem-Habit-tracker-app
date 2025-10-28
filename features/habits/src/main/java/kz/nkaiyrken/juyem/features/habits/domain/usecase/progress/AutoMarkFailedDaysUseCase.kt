package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.first
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.data.repository.HabitRepository
import kz.nkaiyrken.juyem.core.util.DateUtils
import java.time.LocalDate

/**
 * Use Case для автоматической пометки прошедших дней как FAILED.
 *
 * Бизнес-правило:
 * "Все прошедшие дни, которые не были отмечены как SKIPPED, COMPLETED или FAILED,
 * автоматически становятся FAILED"
 *
 * Когда запускать:
 * - При открытии приложения (в Application.onCreate или MainActivity.onCreate)
 * - После полуночи (через WorkManager)
 * - При открытии HabitListScreen
 *
 * Это БИЗНЕС-ПРАВИЛО, поэтому остается в Use Case (не в ViewModel).
 *
 * @param habitRepository для получения списка привычек
 * @param progressRepository для обновления прогресса
 */
class AutoMarkFailedDaysUseCase(
    private val habitRepository: HabitRepository,
    private val progressRepository: DailyProgressRepository
) {
    /**
     * Проверить все активные привычки и отметить прошедшие дни как FAILED.
     *
     * Алгоритм:
     * 1. Получить все активные привычки
     * 2. Для каждой привычки проверить дни с момента создания до вчера
     * 3. Если прогресса нет или статус EMPTY → установить FAILED
     * 4. SKIPPED, COMPLETED, FAILED не трогаем
     */
    suspend operator fun invoke() {
        val habits = habitRepository.getActiveHabits().first()
        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        habits.forEach { habit ->
            // Начало проверки: с даты создания привычки
            val startDate = habit.createdAt.toLocalDate()

            // Конец проверки: вчера (сегодня еще не прошел)
            if (startDate.isAfter(yesterday)) {
                return@forEach // Привычка создана сегодня или в будущем - пропускаем
            }

            // Получаем прогресс за весь период
            val progressMap = progressRepository
                .getProgressForDateRange(startDate, yesterday)
                .first()[habit.id] ?: emptyMap()

            // Проверяем каждый день
            val daysToCheck = DateUtils.getDateRange(startDate, yesterday)

            daysToCheck.forEach { date ->
                // Проверяем есть ли дни из daysOfWeek
                if (!habit.daysOfWeek.contains(date.dayOfWeek)) {
                    return@forEach // Привычка не запланирована на этот день недели - пропускаем
                }

                val progress = progressMap[date]

                // Если прогресса нет или статус EMPTY → ставим FAILED
                if (progress == null || progress.status == DailyProgressStatus.EMPTY) {
                    progressRepository.upsertProgress(
                        DailyProgress(
                            habitId = habit.id,
                            date = date,
                            value = 0,
                            status = DailyProgressStatus.FAILED
                        )
                    )
                }
                // SKIPPED, COMPLETED, FAILED, PARTIAL не трогаем
            }
        }
    }
}

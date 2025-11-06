package kz.nkaiyrken.juyem.features.habits.domain.usecase.progress

import kotlinx.coroutines.flow.first
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.data.repository.HabitRepository
import kz.nkaiyrken.juyem.core.util.DateUtils
import java.time.LocalDate
import javax.inject.Inject

/**
 * Use Case для автоматической пометки прошедших дней как FAILED.
 *
 * Бизнес-правило:
 * "Все прошедшие дни, которые не были отмечены как SKIPPED, COMPLETED или FAILED,
 * автоматически становятся FAILED"
 *
 * ВАЖНО: Обрабатываются только дни в ТЕКУЩЕМ расписании (habit.daysOfWeek).
 * Это предотвращает создание FAILED статусов для дней, добавленных в расписание позже.
 *
 * Пример:
 * - 15 января: habit создана с daysOfWeek=[ПН,СР,ПТ]
 * - 20 января (вторник): нет записи в БД (правильно - не был запланирован)
 * - 25 января: пользователь добавил ВТ: daysOfWeek=[ПН,ВТ,СР,ПТ]
 * - 26 января: AutoMarkFailedDaysUseCase НЕ создаст FAILED для 20 января,
 *   т.к. проверяет только дни в текущем расписании
 *
 * Когда запускать:
 * - При открытии приложения (в Application.onCreate или MainActivity.onCreate)
 * - После полуночи (через WorkManager)
 * - При открытии HabitListScreen
 *
 * @param habitRepository для получения списка привычек
 * @param progressRepository для обновления прогресса
 */
class AutoMarkFailedDaysUseCase @Inject constructor(
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
        val activeHabits = habitRepository.getActiveHabits().first()
        val yesterday = LocalDate.now().minusDays(1)

        activeHabits.forEach { habit ->
            markFailedDaysForHabit(habit, yesterday)
        }
    }

    /**
     * Marks failed days for a single habit
     */
    private suspend fun markFailedDaysForHabit(habit: Habit, yesterday: LocalDate) {
        val startDate = habit.createdAt.toLocalDate()

        // Skip if habit was created today or later
        if (startDate.isAfter(yesterday)) {
            return
        }

        val progressMap = getProgressMapForHabit(habit.id, startDate, yesterday)
        val daysToCheck = DateUtils.getDateRange(startDate, yesterday)

        daysToCheck.forEach { date ->
            processDay(habit, date, progressMap[date])
        }
    }

    /**
     * Gets progress map for habit in date range
     */
    private suspend fun getProgressMapForHabit(
        habitId: Int,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Map<LocalDate, DailyProgress> {
        return progressRepository
            .getProgressForDateRange(startDate, endDate)
            .first()[habitId] ?: emptyMap()
    }

    /**
     * Processes a single day - marks as FAILED if needed
     *
     * Rules:
     * - Only processes days in CURRENT schedule (prevents marking days added later)
     * - Only marks EMPTY/null progress as FAILED
     * - Sets wasScheduled=true (day was in schedule when marked)
     */
    private suspend fun processDay(
        habit: Habit,
        date: LocalDate,
        progress: DailyProgress?,
    ) {
        // Check CURRENT schedule (critical for schedule change handling)
        if (!habit.daysOfWeek.contains(date.dayOfWeek)) {
            return // Day not in current schedule - skip
        }

        // Only mark as FAILED if no progress or EMPTY status
        if (shouldMarkAsFailed(progress)) {
            markDayAsFailed(habit.id, date)
        }
    }

    /**
     * Determines if day should be marked as FAILED
     */
    private fun shouldMarkAsFailed(progress: DailyProgress?): Boolean {
        return progress == null || progress.status == DailyProgressStatus.EMPTY
    }

    /**
     * Marks a specific day as FAILED with wasScheduled flag
     */
    private suspend fun markDayAsFailed(habitId: Int, date: LocalDate) {
        progressRepository.upsertProgress(
            DailyProgress(
                habitId = habitId,
                date = date,
                value = 0,
                status = DailyProgressStatus.FAILED,
                wasScheduled = true // Day was in schedule when marked
            )
        )
    }
}

package kz.nkaiyrken.juyem.features.habits.testutil

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitStatus
import kz.nkaiyrken.juyem.core.HabitType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

/**
 * Test fixtures for creating test data
 */
object TestFixtures {

    /**
     * Creates a default test habit with customizable properties
     */
    fun createHabit(
        id: Int = 1,
        title: String = "Test Habit",
        type: HabitType = HabitType.BOOLEAN,
        daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet(),
        createdAt: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
        status: HabitStatus = HabitStatus.ACTIVE,
        goalValue: Int? = null,
        unit: String? = null,
    ): Habit = Habit(
        id = id,
        title = title,
        type = type,
        daysOfWeek = daysOfWeek,
        createdAt = createdAt,
        status = status,
        goalValue = goalValue,
        unit = unit,
        orderIndex = null,
        remindEnabled = false,
        remindTime = null,
        archivedAt = null
    )

    /**
     * Creates a habit with specific days of week (weekdays only)
     */
    fun createWeekdaysHabit(
        id: Int = 1,
        createdAt: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
    ): Habit = createHabit(
        id = id,
        title = "Weekdays Habit",
        daysOfWeek = setOf(
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY
        ),
        createdAt = createdAt
    )

    /**
     * Creates a habit with specific days of week (weekends only)
     */
    fun createWeekendsHabit(
        id: Int = 2,
        createdAt: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
    ): Habit = createHabit(
        id = id,
        title = "Weekends Habit",
        daysOfWeek = setOf(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY),
        createdAt = createdAt
    )

    /**
     * Creates a counter habit with goal
     */
    fun createCounterHabit(
        id: Int = 3,
        goalValue: Int = 10,
        unit: String = "reps",
        createdAt: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
    ): Habit = createHabit(
        id = id,
        title = "Counter Habit",
        type = HabitType.COUNTER,
        goalValue = goalValue,
        unit = unit,
        createdAt = createdAt
    )

    /**
     * Creates a timer habit with goal
     */
    fun createTimerHabit(
        id: Int = 4,
        goalValue: Int = 3600,
        createdAt: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0),
    ): Habit = createHabit(
        id = id,
        title = "Timer Habit",
        type = HabitType.TIMER,
        goalValue = goalValue,
        unit = "seconds",
        createdAt = createdAt
    )

    /**
     * Creates a daily progress entry
     */
    fun createProgress(
        habitId: Int = 1,
        date: LocalDate = LocalDate.now(),
        status: DailyProgressStatus = DailyProgressStatus.COMPLETED,
        value: Int = 0,
        wasScheduled: Boolean = true,
    ): DailyProgress = DailyProgress(
        habitId = habitId,
        date = date,
        value = value,
        status = status,
        wasScheduled = wasScheduled
    )

    /**
     * Creates a list of completed progress for consecutive days
     */
    fun createCompletedWeek(
        habitId: Int = 1,
        weekStart: LocalDate = LocalDate.now().minusDays(6),
    ): List<DailyProgress> {
        return (0..6).map { dayOffset ->
            createProgress(
                habitId = habitId,
                date = weekStart.plusDays(dayOffset.toLong()),
                status = DailyProgressStatus.COMPLETED
            )
        }
    }

    /**
     * Creates a mixed progress week (some completed, some failed, some skipped)
     */
    fun createMixedWeek(
        habitId: Int = 1,
        weekStart: LocalDate = LocalDate.now().minusDays(6),
    ): List<DailyProgress> {
        val statuses = listOf(
            DailyProgressStatus.COMPLETED,
            DailyProgressStatus.COMPLETED,
            DailyProgressStatus.FAILED,
            DailyProgressStatus.SKIPPED,
            DailyProgressStatus.COMPLETED,
            DailyProgressStatus.EMPTY,
            DailyProgressStatus.EMPTY
        )
        return statuses.mapIndexed { index, status ->
            createProgress(
                habitId = habitId,
                date = weekStart.plusDays(index.toLong()),
                status = status
            )
        }
    }

    /**
     * Creates counter progress with specific values
     */
    fun createCounterProgress(
        habitId: Int = 1,
        date: LocalDate = LocalDate.now(),
        currentValue: Int = 5,
        status: DailyProgressStatus = DailyProgressStatus.PARTIAL,
    ): DailyProgress = DailyProgress(
        habitId = habitId,
        date = date,
        value = currentValue,
        status = status,
        wasScheduled = true
    )
}

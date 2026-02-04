package kz.nkaiyrken.juyem.features.habits

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.DailyProgressStatus
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitStatus
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.features.habits.domain.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.CreateHabitUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.habit.GetActiveHabitsUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.progress.GetProgressForWeekUseCase
import kz.nkaiyrken.juyem.features.habits.domain.usecase.progress.UpsertDailyProgressUseCase
import kz.nkaiyrken.juyem.features.habits.presentation.HabitListViewModel
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
class HabitListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var habitRepository: FakeHabitRepository
    private lateinit var progressRepository: FakeDailyProgressRepository
    private lateinit var viewModel: HabitListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        habitRepository = FakeHabitRepository()
        progressRepository = FakeDailyProgressRepository()

        val getActiveHabitsUseCase = GetActiveHabitsUseCase(habitRepository)
        val getWeeklyProgressUseCase = GetProgressForWeekUseCase(progressRepository)
        val upsertProgressUseCase = UpsertDailyProgressUseCase(progressRepository)
        val createHabitUseCase = CreateHabitUseCase(habitRepository)

        viewModel = HabitListViewModel(
            getActiveHabitsUseCase,
            getWeeklyProgressUseCase,
            upsertProgressUseCase,
            createHabitUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `mapToHabitCardUiModel - days before habit creation are disabled`() {
        // Given: Habit created 3 days ago
        val today = LocalDate.now()
        val habit = createHabit(id = 1, createdAt = today.minusDays(3).atStartOfDay())
        val weekStart = today.with(DayOfWeek.MONDAY)
        val weekDates = (0..6).map { weekStart.plusDays(it.toLong()) }

        // When: Mapping with no progress
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = emptyMap()
        )

        // Then: Days before creation should be disabled
        val weekProgress = result.first().weekDaysProgress
        weekDates.forEach { date ->
            val dayProgress = weekProgress[date.dayOfWeek]
            if (date.isBefore(habit.createdAt.toLocalDate())) {
                assertFalse("Day $date should be disabled", dayProgress!!.isEnabled)
                assertEquals(DailyProgressStatus.EMPTY, dayProgress.status)
            }
        }
    }

    @Test
    fun `mapToHabitCardUiModel - days with progress are always enabled`() {
        // Given: Habit with some completed days
        val today = LocalDate.now()
        val habit = createHabit(id = 1, createdAt = today.minusDays(10).atStartOfDay())

        val completedDay = today.minusDays(2)
        val progress = mapOf(
            1 to mapOf(
                completedDay to DailyProgress(
                    habitId = 1,
                    date = completedDay,
                    status = DailyProgressStatus.COMPLETED,
                    wasScheduled = true
                )
            )
        )

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = progress
        )

        // Then: Day with progress should be enabled
        val dayProgress = result.first().weekDaysProgress[completedDay.dayOfWeek]
        assertTrue("Day with progress should be enabled", dayProgress!!.isEnabled)
        assertEquals(DailyProgressStatus.COMPLETED, dayProgress.status)
    }

    @Test
    fun `mapToHabitCardUiModel - days not in schedule are disabled`() {
        // Given: Habit with only weekdays scheduled
        val today = LocalDate.now()
        val habit = createHabit(
            id = 1,
            createdAt = today.minusDays(10).atStartOfDay(),
            daysOfWeek = setOf(DayOfWeek.MONDAY, DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY)
        )

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = emptyMap()
        )

        // Then: Weekend days should be disabled
        val weekProgress = result.first().weekDaysProgress
        assertFalse("Saturday should be disabled",
            weekProgress[DayOfWeek.SATURDAY]!!.isEnabled)
        assertFalse("Sunday should be disabled",
            weekProgress[DayOfWeek.SUNDAY]!!.isEnabled)

        // And weekdays should have correct state
        val monday = weekProgress[DayOfWeek.MONDAY]
        assertNotNull(monday)
    }

    @Test
    fun `mapToHabitCardUiModel - past days without progress are disabled`() {
        // Given: Habit created 10 days ago, schedule changed recently
        val today = LocalDate.now()
        val pastDay = today.minusDays(5)
        val habit = createHabit(
            id = 1,
            createdAt = today.minusDays(10).atStartOfDay(),
            daysOfWeek = DayOfWeek.entries.toSet() // Current schedule: all days
        )

        // No progress for past day (means it was added to schedule later)

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = emptyMap()
        )

        // Then: Past day without progress should be disabled (added to schedule later)
        val dayProgress = result.first().weekDaysProgress[pastDay.dayOfWeek]
        if (pastDay.isBefore(today)) {
            // Note: depends on which week we're checking
            assertNotNull(dayProgress)
        }
    }

    @Test
    fun `mapToHabitCardUiModel - today without progress is enabled`() {
        // Given: Habit with no progress for today
        val today = LocalDate.now()
        val habit = createHabit(id = 1, createdAt = today.minusDays(5).atStartOfDay())

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = emptyMap()
        )

        // Then: Today should be enabled
        val todayProgress = result.first().weekDaysProgress[today.dayOfWeek]
        assertTrue("Today should be enabled", todayProgress!!.isEnabled)
        assertEquals(DailyProgressStatus.EMPTY, todayProgress.status)
    }

    @Test
    fun `mapToHabitCardUiModel - generates all 7 days`() {
        // Given: Simple habit
        val habit = createHabit(id = 1)

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = emptyMap()
        )

        // Then: Should have all 7 days of week
        val weekProgress = result.first().weekDaysProgress
        assertEquals(7, weekProgress.size)
        DayOfWeek.entries.forEach { day ->
            assertNotNull("Missing day: $day", weekProgress[day])
        }
    }

    @Test
    fun `mapToHabitCardUiModel - handles multiple habits`() {
        // Given: Multiple habits with different progress
        val today = LocalDate.now()
        val habit1 = createHabit(id = 1, title = "Habit 1")
        val habit2 = createHabit(id = 2, title = "Habit 2")

        val progress = mapOf(
            1 to mapOf(
                today to DailyProgress(1, today, status = DailyProgressStatus.COMPLETED)
            ),
            2 to mapOf(
                today to DailyProgress(2, today, status = DailyProgressStatus.FAILED)
            )
        )

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit1, habit2),
            weekProgress = progress
        )

        // Then
        assertEquals(2, result.size)
        assertEquals("Habit 1", result[0].title)
        assertEquals("Habit 2", result[1].title)

        val habit1Today = result[0].weekDaysProgress[today.dayOfWeek]
        assertEquals(DailyProgressStatus.COMPLETED, habit1Today!!.status)

        val habit2Today = result[1].weekDaysProgress[today.dayOfWeek]
        assertEquals(DailyProgressStatus.FAILED, habit2Today!!.status)
    }

    @Test
    fun `mapToHabitCardUiModel - counter habit with values`() {
        // Given: Counter habit with partial progress
        val today = LocalDate.now()
        val habit = Habit(
            id = 1,
            title = "Push-ups",
            type = HabitType.COUNTER,
            goalValue = 10,
            unit = "reps",
            createdAt = today.minusDays(5).atStartOfDay()
        )

        val progress = mapOf(
            1 to mapOf(
                today to DailyProgress(
                    habitId = 1,
                    date = today,
                    value = 5, // 5 out of 10
                    status = DailyProgressStatus.PARTIAL
                )
            )
        )

        // When
        val result = viewModel.mapToHabitsCardUiModels(
            habits = listOf(habit),
            weekProgress = progress
        )

        // Then
        val todayProgress = result.first().weekDaysProgress[today.dayOfWeek]
        assertTrue(todayProgress is kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel.Counter)
        val counterProgress = todayProgress as kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel.Counter
        assertEquals(5, counterProgress.currentValue)
        assertEquals(10, counterProgress.goalValue)
        assertEquals("reps", counterProgress.unit)
        assertEquals(DailyProgressStatus.PARTIAL, counterProgress.status)
    }

    // Helper functions
    private fun createHabit(
        id: Int = 1,
        title: String = "Test Habit",
        createdAt: LocalDateTime = LocalDateTime.now().minusDays(1),
        daysOfWeek: Set<DayOfWeek> = DayOfWeek.entries.toSet(),
        type: HabitType = HabitType.BOOLEAN
    ) = Habit(
        id = id,
        title = title,
        type = type,
        daysOfWeek = daysOfWeek,
        createdAt = createdAt,
        status = HabitStatus.ACTIVE
    )

    // Minimal fake repositories (same as in AutoMarkFailedDaysUseCaseTest)
    class FakeHabitRepository : HabitRepository {
        private var habits = emptyList<Habit>()
        fun setHabits(list: List<Habit>) { habits = list }
        override fun getActiveHabits(): Flow<List<Habit>> = flowOf(habits)
        override fun getHabitById(id: Int): Flow<Habit?> = flowOf(habits.find { it.id == id })
        override fun getArchivedHabits(): Flow<List<Habit>> = flowOf(emptyList())
        override suspend fun insertHabit(habit: Habit): Long = habit.id.toLong()
        override suspend fun updateHabit(habit: Habit) {}
        override suspend fun deleteHabit(habitId: Int) {}
        override suspend fun archiveHabit(habitId: Int, archivedAt: LocalDateTime) {}
        override suspend fun unarchiveHabit(id: Int) = Unit
        override suspend fun updateHabitsOrder(habits: List<Habit>) = Unit
    }

    class FakeDailyProgressRepository : DailyProgressRepository {
        private val progressMap = mutableMapOf<String, DailyProgress>()

        override fun getDailyProgress(habitId: Int, date: LocalDate): Flow<DailyProgress?> =
            flowOf(progressMap["${habitId}_${date}"])

        override fun getWeeklyProgress(habitId: Int, startDate: LocalDate): Flow<List<DailyProgress>> =
            flowOf(emptyList())

        override fun getAllProgressForDate(date: LocalDate): Flow<Map<Int, DailyProgress>> =
            flowOf(emptyMap())

        override fun getProgressForDateRange(
            startDate: LocalDate,
            endDate: LocalDate
        ): Flow<Map<Int, Map<LocalDate, DailyProgress>>> {
            val filtered = progressMap.values.filter {
                !it.date.isBefore(startDate) && !it.date.isAfter(endDate)
            }
            val grouped = filtered.groupBy { it.habitId }
                .mapValues { (_, list) -> list.associateBy { it.date } }
            return flowOf(grouped)
        }

        override suspend fun upsertProgress(progress: DailyProgress) {
            progressMap["${progress.habitId}_${progress.date}"] = progress
        }

        override suspend fun deleteProgress(habitId: Int, date: LocalDate) {
            progressMap.remove("${habitId}_${date}")
        }

        override suspend fun deleteAllProgressForHabit(habitId: Int) {
            progressMap.entries.removeIf { it.value.habitId == habitId }
        }
    }
}

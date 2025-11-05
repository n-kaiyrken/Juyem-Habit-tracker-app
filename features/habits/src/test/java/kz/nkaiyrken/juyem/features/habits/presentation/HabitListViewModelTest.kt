package kz.nkaiyrken.juyem.features.habits.presentation

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDate

class HabitListViewModelTest {

    @Test
    fun mapToHabitCardUiModel_WithEmptyProgress_ReturnsListOfHabitCardUiModelWithEmptyProgress() {
        val habits: List<Habit> = listOf(
            Habit(
                id = 0,
                title = "Habit 0",
                orderIndex = 0,
                type = HabitType.BOOLEAN,
            ),
            Habit(
                id = 1,
                title = "Habit 1",
                orderIndex = 1,
                type = HabitType.COUNTER,
                goalValue = 10,
                unit = "km",
            ),
            Habit(
                id = 2,
                title = "Habit 2",
                orderIndex = 2,
                type = HabitType.TIMER,
                goalValue = 10,
                unit = "min",
            ),
        )
        val weekProgress: Map<Int, Map<LocalDate, DailyProgress>> = emptyMap()

        val habitCardUiModelList: List<HabitCardUiModel> = mapToHabitsCardUiModels(habits, weekProgress)

        assertEquals(3, habitCardUiModelList.size)

        habitCardUiModelList.forEach { habitCardUiModel ->
            assertEquals(habitCardUiModel.weekDaysProgress, emptyMap<DayOfWeek, DailyProgressUiModel>())
            assertEquals(habitCardUiModel.title, "Habit ${habitCardUiModel.habitId}")
        }
    }
}
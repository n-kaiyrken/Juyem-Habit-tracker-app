package kz.nkaiyrken.juyem.features.habits.presentation

import kz.nkaiyrken.juyem.core.DailyProgress
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.HabitType
import kz.nkaiyrken.juyem.features.habits.presentation.models.DailyProgressUiModel
import kz.nkaiyrken.juyem.features.habits.presentation.models.HabitCardUiModel
import java.time.DayOfWeek
import java.time.LocalDate

fun mapToHabitsCardUiModels(
    habits: List<Habit>,
    weekProgress: Map<Int, Map<LocalDate, DailyProgress>>,
): List<HabitCardUiModel> {
    val habitCardUiModelList: List<HabitCardUiModel> = habits.map { habit ->

        val weekProgressForOneHabit: Map<LocalDate, DailyProgress> =
            weekProgress[habit.id] ?: emptyMap()

        val weekDaysProgress: Map<DayOfWeek, DailyProgressUiModel> =
            weekProgressForOneHabit.map { (key, value) ->
                Pair(key.dayOfWeek, createDailyProgressUiModelFromExistingEntity(habit, value))
            }.toMap()

        HabitCardUiModel(
            habitId = habit.id,
            title = habit.title,
            habitType = habit.type,
            weekDaysProgress = weekDaysProgress,
        )
    }
    return habitCardUiModelList
}

fun createDailyProgressUiModelFromExistingEntity(
    habit: Habit,
    dailyProgress: DailyProgress,
): DailyProgressUiModel {
    return when (habit.type) {
        HabitType.BOOLEAN -> DailyProgressUiModel.TypeBoolean(
            habitId = dailyProgress.habitId,
            date = dailyProgress.date,
            status = dailyProgress.status,
        )

        HabitType.COUNTER -> DailyProgressUiModel.Counter(
            currentValue = dailyProgress.value,
            goalValue = habit.goalValue ?: 0,
            unit = habit.unit ?: "",
            habitId = dailyProgress.habitId,
            date = dailyProgress.date,
            status = dailyProgress.status,
        )

        HabitType.TIMER -> DailyProgressUiModel.Timer(
            currentValue = dailyProgress.value,
            goalValue = habit.goalValue ?: 0,
            habitId = dailyProgress.habitId,
            date = dailyProgress.date,
            status = dailyProgress.status,
        )
    }
}
package kz.nkaiyrken.juyem.features.habits.data.local

import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.features.habits.data.local.entity.HabitEntity
import javax.inject.Inject

class HabitsLocalDataSource @Inject constructor(
    private val habitsDao: HabitsDao
) {
    val habits: Flow<List<HabitEntity>> = habitsDao.getHabits()

    suspend fun add(habit: HabitEntity) = habitsDao.insertHabit(habit)
}
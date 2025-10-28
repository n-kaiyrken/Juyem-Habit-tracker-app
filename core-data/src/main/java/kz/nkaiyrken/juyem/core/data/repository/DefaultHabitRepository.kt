package kz.nkaiyrken.juyem.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.nkaiyrken.database.local.dao.HabitDao
import kz.nkaiyrken.juyem.core.Habit
import kz.nkaiyrken.juyem.core.data.mapper.HabitMapper
import java.time.LocalDateTime
import javax.inject.Inject

class DefaultHabitRepository @Inject constructor(
    private val habitDao: HabitDao,
    private val mapper: HabitMapper
) : HabitRepository {

    override fun getActiveHabits(): Flow<List<Habit>> =
        habitDao.getActiveHabits()
            .map { entities -> mapper.toDomainList(entities) }

    override fun getHabitById(id: Int): Flow<Habit?> =
        habitDao.getHabitById(id)
            .map { entity -> entity?.let { mapper.toDomain(it) } }

    override fun getArchivedHabits(): Flow<List<Habit>> =
        habitDao.getArchivedHabits()
            .map { entities -> mapper.toDomainList(entities) }

    override suspend fun insertHabit(habit: Habit): Long {
        val entity = mapper.toEntity(habit)
        return habitDao.insert(entity)
    }

    override suspend fun updateHabit(habit: Habit) {
        val entity = mapper.toEntity(habit)
        habitDao.update(entity)
    }

    override suspend fun deleteHabit(id: Int) {
        habitDao.deleteById(id)
    }

    override suspend fun archiveHabit(id: Int, archivedAt: LocalDateTime) {
        habitDao.archiveHabit(id, archivedAt)
    }

    override suspend fun unarchiveHabit(id: Int) {
        habitDao.unarchiveHabit(id)
    }

    override suspend fun updateHabitsOrder(habits: List<Habit>) {
        habits.forEach { habit ->
            updateHabit(habit)
        }
    }
}

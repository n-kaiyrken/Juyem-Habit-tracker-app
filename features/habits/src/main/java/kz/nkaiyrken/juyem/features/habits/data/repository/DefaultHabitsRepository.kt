package kz.nkaiyrken.juyem.features.habits.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kz.nkaiyrken.juyem.features.habits.data.local.HabitsLocalDataSource
import kz.nkaiyrken.juyem.features.habits.data.mappers.HabitsMapper
import kz.nkaiyrken.juyem.features.habits.domain.model.Habit
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitsRepository
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(
    private val mapper: HabitsMapper,
    private val localDataSource: HabitsLocalDataSource,
) : HabitsRepository {

    override val habits: Flow<List<String>> =
        localDataSource.habits.map { items -> items.map { mapper.mapHabitEntityToDomain(it).name } }

    override suspend fun add(name: String) {
        localDataSource.add(mapper.mapHabitToEntity(Habit(name = name)))
    }
}
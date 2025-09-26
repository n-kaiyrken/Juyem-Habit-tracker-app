package kz.nkaiyrken.juyem.features.habits.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kz.nkaiyrken.juyem.features.habits.data.repository.DefaultHabitsRepository
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitsRepository
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsHabitRepository(
        habitRepository: DefaultHabitsRepository
    ): HabitsRepository

}

class FakeHabitRepository @Inject constructor() : HabitsRepository {
    override val habits: Flow<List<String>> = flowOf(fakeHabits)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeHabits = listOf("One", "Two", "Three")
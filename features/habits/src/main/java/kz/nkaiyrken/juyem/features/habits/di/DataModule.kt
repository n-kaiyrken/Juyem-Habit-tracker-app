package kz.nkaiyrken.juyem.features.habits.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.nkaiyrken.juyem.features.habits.data.repository.DefaultHabitsRepository
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitsRepository
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
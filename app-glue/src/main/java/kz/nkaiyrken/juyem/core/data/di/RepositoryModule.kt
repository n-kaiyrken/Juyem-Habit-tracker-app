package kz.nkaiyrken.juyem.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.nkaiyrken.juyem.core.data.repository.DailyProgressRepositoryImpl
import kz.nkaiyrken.juyem.core.data.repository.HabitRepositoryImpl
import kz.nkaiyrken.juyem.features.habits.domain.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.features.habits.domain.repository.HabitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        impl: HabitRepositoryImpl
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindDailyProgressRepository(
        impl: DailyProgressRepositoryImpl
    ): DailyProgressRepository
}

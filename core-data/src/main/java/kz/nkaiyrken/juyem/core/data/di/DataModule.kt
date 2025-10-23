package kz.nkaiyrken.juyem.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.nkaiyrken.juyem.core.data.repository.DefaultDailyProgressRepository
import kz.nkaiyrken.juyem.core.data.repository.DefaultHabitRepository
import kz.nkaiyrken.juyem.core.domain.repository.DailyProgressRepository
import kz.nkaiyrken.juyem.core.domain.repository.HabitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindHabitRepository(
        impl: DefaultHabitRepository
    ): HabitRepository

    @Binds
    @Singleton
    abstract fun bindDailyProgressRepository(
        impl: DefaultDailyProgressRepository
    ): DailyProgressRepository
}

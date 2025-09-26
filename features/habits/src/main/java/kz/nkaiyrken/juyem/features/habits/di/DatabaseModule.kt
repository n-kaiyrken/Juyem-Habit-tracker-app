package kz.nkaiyrken.juyem.features.habits.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.nkaiyrken.juyem.features.habits.data.local.HabitsDao
import kz.nkaiyrken.juyem.features.habits.data.local.HabitsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    fun provideHabitsDao(habitsDatabase: HabitsDatabase): HabitsDao {
        return habitsDatabase.habitDao()
    }

    @Provides
    @Singleton
    fun provideHabitsDatabase(@ApplicationContext appContext: Context): HabitsDatabase {
        return Room.databaseBuilder(
            appContext,
            HabitsDatabase::class.java,
            "Habit"
        ).build()
    }
}
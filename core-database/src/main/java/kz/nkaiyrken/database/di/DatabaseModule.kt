package kz.nkaiyrken.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.nkaiyrken.database.local.JuyemDatabase
import kz.nkaiyrken.database.local.dao.DailyProgressDao
import kz.nkaiyrken.database.local.dao.HabitDao
import kz.nkaiyrken.database.local.dao.HabitPlanDao
import kz.nkaiyrken.database.local.dao.NoteDao
import kz.nkaiyrken.database.local.dao.StatisticsDao
import kz.nkaiyrken.database.local.dao.TimerSessionDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideJuyemDatabase(@ApplicationContext appContext: Context): JuyemDatabase {
        return JuyemDatabase.Companion.getDatabase(appContext)
    }

    @Provides
    fun provideHabitDao(
        juyemDatabase: JuyemDatabase,
    ): HabitDao = juyemDatabase.habitDao()

    @Provides
    fun provideDailyProgressDao(
        juyemDatabase: JuyemDatabase,
    ): DailyProgressDao = juyemDatabase.dailyProgressDao()

    @Provides
    fun provideTimerSessionDao(
        juyemDatabase: JuyemDatabase,
    ): TimerSessionDao = juyemDatabase.timerSessionDao()

    @Provides
    fun provideNoteDao(
        juyemDatabase: JuyemDatabase,
    ): NoteDao = juyemDatabase.noteDao()

    @Provides
    fun provideStatisticsDao(
        juyemDatabase: JuyemDatabase,
    ): StatisticsDao = juyemDatabase.statisticsDao()

    @Provides
    fun provideHabitPlanDao(
        juyemDatabase: JuyemDatabase,
    ): HabitPlanDao = juyemDatabase.habitPlanDao()
}
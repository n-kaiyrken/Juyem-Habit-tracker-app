package kz.nkaiyrken.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kz.nkaiyrken.database.local.converters.Converters
import kz.nkaiyrken.database.local.dao.DailyProgressDao
import kz.nkaiyrken.database.local.dao.HabitDao
import kz.nkaiyrken.database.local.dao.HabitPlanDao
import kz.nkaiyrken.database.local.dao.NoteDao
import kz.nkaiyrken.database.local.dao.StatisticsDao
import kz.nkaiyrken.database.local.dao.TimerSessionDao
import kz.nkaiyrken.database.local.entity.DailyProgressEntity
import kz.nkaiyrken.database.local.entity.HabitEntity
import kz.nkaiyrken.database.local.entity.HabitPlanEntity
import kz.nkaiyrken.database.local.entity.NoteEntity
import kz.nkaiyrken.database.local.entity.StatisticsEntity
import kz.nkaiyrken.database.local.entity.TimerSessionEntity

@Database(
    entities = [
        HabitEntity::class,
        DailyProgressEntity::class,
        TimerSessionEntity::class,
        NoteEntity::class,
        StatisticsEntity::class,
        HabitPlanEntity::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class JuyemDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitDao
    abstract fun dailyProgressDao(): DailyProgressDao
    abstract fun timerSessionDao(): TimerSessionDao
    abstract fun noteDao(): NoteDao
    abstract fun statisticsDao(): StatisticsDao
    abstract fun habitPlanDao(): HabitPlanDao

    companion object {

        @Volatile
        private var INSTANCE: JuyemDatabase? = null

        fun getDatabase(context: Context): JuyemDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JuyemDatabase::class.java,
                    "juyem_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
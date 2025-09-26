package kz.nkaiyrken.juyem.features.habits.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.nkaiyrken.juyem.features.habits.data.local.entity.HabitEntity

@Database(entities = [HabitEntity::class], version = 1)
abstract class HabitsDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitsDao
}
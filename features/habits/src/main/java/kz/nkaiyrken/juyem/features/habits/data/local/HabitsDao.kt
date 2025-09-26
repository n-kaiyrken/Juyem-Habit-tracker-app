package kz.nkaiyrken.juyem.features.habits.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.juyem.features.habits.data.local.entity.HabitEntity

@Dao
interface HabitsDao {
    @Query("SELECT * FROM habits ORDER BY uid DESC LIMIT 10")
    fun getHabits(): Flow<List<HabitEntity>>

    @Insert
    suspend fun insertHabit(item: HabitEntity)
}
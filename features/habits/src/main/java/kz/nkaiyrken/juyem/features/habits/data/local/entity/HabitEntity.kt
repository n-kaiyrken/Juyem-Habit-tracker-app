package kz.nkaiyrken.juyem.features.habits.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class HabitEntity(
    val name: String
) {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0
}
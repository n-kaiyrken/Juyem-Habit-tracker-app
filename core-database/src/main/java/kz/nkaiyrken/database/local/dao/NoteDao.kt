package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.NoteEntity
import java.time.LocalDate

@Dao
interface NoteDao: BaseDao<NoteEntity> {
    @Query("SELECT * FROM notes WHERE habit_id = :habitId AND date = :date ORDER BY created_at DESC")
    fun getNotesForDate(habitId: Int, date: LocalDate): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity?>
}
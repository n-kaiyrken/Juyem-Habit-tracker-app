package kz.nkaiyrken.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import kz.nkaiyrken.database.local.entity.NoteEntity
import java.time.LocalDate

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE habit_id = :habitId AND date = :date ORDER BY created_at DESC")
    fun getNotesForDate(habitId: Int, date: LocalDate): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE note_id = :noteId")
    fun getNoteById(noteId: Int): Flow<NoteEntity?>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)
}
package com.mksoftware101.personalnotes.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note_table")
    fun getNoteEntityList(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM note_table WHERE id = :id")
    suspend fun getNoteBy(id: Long): NoteEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vararg note: NoteEntity)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Update
    suspend fun update(vararg noteEntity: NoteEntity)
}
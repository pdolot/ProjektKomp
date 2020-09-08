package com.example.myapplication.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.database.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun insert(note: Note)

    @Query("SELECT * FROM note")
    fun getAll(): LiveData<List<Note>>

    @Query("DELETE FROM note WHERE id = :id")
    suspend fun deleteById(id: Long)
}
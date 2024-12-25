package com.example.daybookv2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import androidx.room.Update

@Dao
interface NoteDAO {
    @Query("SELECT * FROM NoteEntity")
    suspend fun getALl(): List<DataModel>

    @Insert
    suspend fun insertNote(task: DataModel)

    @Update
    suspend fun updateNote(note: DataModel)

    @Delete
    suspend fun deleteNote(note: DataModel)
}

package com.example.daybookv2.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDAO
}
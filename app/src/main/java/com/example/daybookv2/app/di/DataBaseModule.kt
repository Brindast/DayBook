package com.example.daybookv2.app.di

import android.app.Application
import androidx.room.Room
import com.example.daybookv2.database.AppDatabase
import com.example.daybookv2.database.NoteDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Provides
    @Singleton
    fun provideDataBase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "notes_database"
        ).build()
    }

    @Provides
    fun provideNoteDao(database: AppDatabase): NoteDAO {
        return database.noteDao()
    }
}
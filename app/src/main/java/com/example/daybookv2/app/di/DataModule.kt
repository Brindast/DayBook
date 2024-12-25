package com.example.daybookv2.app.di

import com.example.daybookv2.database.NoteDAO
import com.example.daybookv2.database.NoteRepImpl
import com.example.daybookv2.domain.repository.NoteRep
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideNoteRep(noteDAO: NoteDAO): NoteRep {
        return NoteRepImpl(noteDAO)
    }
}
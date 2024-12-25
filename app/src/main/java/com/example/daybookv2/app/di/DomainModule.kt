package com.example.daybookv2.app.di

import com.example.daybookv2.domain.repository.NoteRep
import com.example.daybookv2.domain.usecase.DeleteNoteUseCase
import com.example.daybookv2.domain.usecase.GetAllNotesUseCase
import com.example.daybookv2.domain.usecase.InsertNewNoteUseCase
import com.example.daybookv2.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideGetAllNotesUseCase(noteRep: NoteRep): GetAllNotesUseCase {
        return GetAllNotesUseCase(noteRep)
    }

    @Provides
    fun provideInsertNewNoteUseCase(noteRep: NoteRep): InsertNewNoteUseCase {
        return InsertNewNoteUseCase(noteRep = noteRep)
    }

    @Provides
    fun provideUpdateNoteUserCase(noteRep: NoteRep): UpdateNoteUseCase {
        return UpdateNoteUseCase(noteRep = noteRep)
    }

    @Provides
    fun provideDeleteNoteUSeCase(noteRep: NoteRep): DeleteNoteUseCase{
        return DeleteNoteUseCase(noteRep = noteRep)
    }

}
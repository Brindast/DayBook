package com.example.daybookv2.domain.repository

import com.example.daybookv2.domain.model.NoteModel

interface NoteRep {
    suspend fun getAllNotes(): List<NoteModel>

    suspend fun insertNote(note: NoteModel)

    suspend fun updateNote(note: NoteModel)

    suspend fun deleteNote(note: NoteModel)
}
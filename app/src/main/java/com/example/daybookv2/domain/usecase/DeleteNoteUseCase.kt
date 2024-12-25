package com.example.daybookv2.domain.usecase

import com.example.daybookv2.domain.model.NoteModel
import com.example.daybookv2.domain.repository.NoteRep

class DeleteNoteUseCase(private val noteRep: NoteRep) {
    suspend operator fun invoke(note: NoteModel) = noteRep.deleteNote(note)
}
package com.example.daybookv2.database


import com.example.daybookv2.domain.model.NoteModel
import com.example.daybookv2.domain.repository.NoteRep
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepImpl (private val noteDAO: NoteDAO) : NoteRep {
    override suspend fun getAllNotes(): List<NoteModel> {
        return withContext(Dispatchers.IO){
            noteDAO.getALl().map {
                it.toModel()
            }
        }
    }

    override suspend fun insertNote(note: NoteModel) {
        withContext(Dispatchers.IO) {
            noteDAO.insertNote(note.toEntity())
        }
    }

    override suspend fun updateNote(note: NoteModel) {
        withContext(Dispatchers.IO) {
            noteDAO.updateNote(note.toEntity())
        }
    }

    override suspend fun deleteNote(note: NoteModel) {
        withContext(Dispatchers.IO) {
            noteDAO.deleteNote(note.toEntity())
        }
    }

    private fun NoteModel.toEntity() = DataModel(id, dateStart, dateFinish, name, description)
    private fun DataModel.toModel() = NoteModel(id, dateStart, dateFinish, name, description)
}
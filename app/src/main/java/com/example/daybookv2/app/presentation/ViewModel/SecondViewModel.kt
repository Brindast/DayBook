package com.example.daybookv2.app.presentation.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daybookv2.domain.model.NoteModel
import com.example.daybookv2.domain.usecase.DeleteNoteUseCase
import com.example.daybookv2.domain.usecase.InsertNewNoteUseCase
import com.example.daybookv2.domain.usecase.UpdateNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class SecondViewModel @Inject constructor(
    private val insertNewNoteUseCase: InsertNewNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase
) : ViewModel() {

    private var note = NoteModel(
        id = 0,
        dateStart = 0L,
        dateFinish = 0L,
        name = "",
        description = ""
    )
    private val _date = MutableLiveData<Long>()
    val date: LiveData<Long> = _date

    private val _time = MutableLiveData<Int>()
    val time: LiveData<Int> = _time

    private val _header = MutableLiveData<String>()
    val header: LiveData<String> = _header

    private val _description = MutableLiveData<String>()
    val description: LiveData<String> = _description

    fun setTime(time: Int) {
        _time.value = time
        setFullDate()
    }

    fun setDate(date: Long) {
        _date.value = date
        setFullDate()
    }

    fun setHeader(header: String) {
        _header.value = header
        note = note.copy(name = header)
    }

    fun setDescription(description: String) {
        _description.value = description
        note = note.copy(description = description)
    }

    private fun setFullDate() {
        val timeStart = _time.value ?: return
        val date = _date.value ?: return

        val timeFinish = (timeStart + 1) % 24

        val dateStart = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, timeStart)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val dateFinish = Calendar.getInstance().apply {
            timeInMillis = date
            set(Calendar.HOUR_OF_DAY, timeFinish)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        note = note.copy(
            dateStart = dateStart,
            dateFinish = dateFinish
        )
    }

    fun setNote(
                id: Int,
                dateStart: Long,
                dateFinish: Long,
                name: String,
                description: String
    ) {
        note = note.copy(
            id = id,
            dateStart = dateStart,
            dateFinish = dateFinish,
            name = name,
            description = description
        )
        Log.v("SetNote", "Set")
        setTime(
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(note.dateStart),
                ZoneId.systemDefault()
            ).hour
        )
        Log.v("SetTime", "SetTime ${dateStart}")
        setDate(note.dateStart)
        Log.v("SetDat", "SetDate")
        _header.value = name
        _description.value = description
    }

    fun insertNewNote() {
        viewModelScope.launch {
            insertNewNoteUseCase(note)
        }
    }

    fun updateNote() {
        viewModelScope.launch {
            updateNoteUseCase(note)
        }
    }

    fun deleteNote(){
        viewModelScope.launch {
            Log.d("SecondViewModel", "Deleting note: ${note.name}")
            deleteNoteUseCase(note)

        }
    }
}
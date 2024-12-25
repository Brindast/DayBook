package com.example.daybookv2.app.presentation.ViewModel

import android.icu.util.Calendar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.daybookv2.domain.model.NoteModel
import com.example.daybookv2.domain.usecase.GetAllNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllNotesUseCase: GetAllNotesUseCase
) : ViewModel() {

    val _listNote = MutableLiveData<List<NoteModel>>()
    val listNote: LiveData<List<NoteModel>> =
        _listNote.switchMap { noteModels ->
            _date.map { selectedDate ->
                noteModels.filter { note ->
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val noteDateString = dateFormat.format(note.dateStart)

                    // Преобразуем selectedDate в миллисекунды, если это необходимо
                    val selectedDateString = dateFormat.format(selectedDate)

                    noteDateString == selectedDateString
                }
            }
        }



    private val _selectedNote = MutableLiveData<NoteModel>()
    val selectedNote: LiveData<NoteModel> = _selectedNote

    private val _date = MutableLiveData<Long>().apply {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        value = calendar.timeInMillis
    }
    val date: LiveData<Long> = _date


    fun setDateFromCalendar(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth, 0, 0, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
        _date.value = calendar
    }


    fun selectedNote(note: NoteModel) {
        _selectedNote.value = note
    }

    fun getAll() {
        viewModelScope.launch {
            _listNote.value = getAllNotesUseCase()
        }
    }

}
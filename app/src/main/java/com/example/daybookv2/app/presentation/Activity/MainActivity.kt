package com.example.daybookv2.app.presentation.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daybookv2.app.presentation.Adapter.AdapterNotes
import com.example.daybookv2.app.presentation.ViewModel.MainViewModel
import com.example.daybookv2.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getAll()

        val btnCreateNew = binding.btnCreateNew
        val calendarView = binding.calendarView
        val notesRecyclerView = binding.rvNotes

        val adapterNotes = AdapterNotes(
            mutableListOf(),
            onNoteSelected = { note ->
                mainViewModel.selectedNote(note)
            }
        )

        notesRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        notesRecyclerView.adapter = adapterNotes

        mainViewModel.listNote.observe(this, { notes ->
            adapterNotes.updateData(notes)
            Log.v("listNote", "Updated list: $notes, ${mainViewModel.date.value!!}")
        })
        mainViewModel._listNote.observe(this, { notes ->
            Log.v("_listNote", "List: $notes, ${mainViewModel.date.value!!}")
        })

        mainViewModel.date.observe(this, { date ->
            date?.let {
                calendarView.date = it
            }
        })

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            mainViewModel.setDateFromCalendar(year, month, dayOfMonth)
        }

        val secondActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    mainViewModel.getAll()
                }
            }

        btnCreateNew.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("isEditing", false)
            intent.putExtra("id", 0)
            intent.putExtra("date_start", mainViewModel.date.value!!)
            Log.v("Create", "${mainViewModel.date.value}")
            intent.putExtra("date_finish", mainViewModel.date.value!!)
            intent.putExtra("name", "")
            intent.putExtra("description", "")
            secondActivityLauncher.launch(intent)
        }

        mainViewModel.selectedNote.observe(this, { note ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("isEditing", true)
            intent.putExtra("id", note.id)
            intent.putExtra("date_start", note.dateStart)
            intent.putExtra("date_finish", note.dateFinish)
            intent.putExtra("name", note.name)
            intent.putExtra("description", note.description)
            secondActivityLauncher.launch(intent)
        })
    }

}
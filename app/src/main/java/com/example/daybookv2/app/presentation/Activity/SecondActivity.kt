package com.example.daybookv2.app.presentation.Activity

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.daybookv2.app.presentation.ViewModel.SecondViewModel
import com.example.daybookv2.databinding.ActivitySecondBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {

    private val secondViewModel: SecondViewModel by viewModels()
    private lateinit var binding: ActivitySecondBinding
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val llBtnChangeTime = binding.llBtnChangeTime
        val llBtnChangeDate = binding.llBtnChangeDate
        val dateTextView = binding.tvDate
        val timeTextView = binding.tvTime
        val mainHeader = binding.mainHeader
        val etDescription = binding.etDescription
        val btnBack = binding.btnBack
        val btnDelete = binding.btnDelete
        val btnSave = binding.btnSave

        val isEditing = intent.getBooleanExtra("isEditing", false)
        val name = intent.getStringExtra("name") ?: ""
        val description = intent.getStringExtra("description") ?: ""

        mainHeader.setText(name)
        etDescription.setText(description)
        Log.v("date", "${dateFormat.format(intent.getLongExtra("dateStart", 0L))}")

        secondViewModel.setNote(
            intent.getIntExtra("id", 0),
            intent.getLongExtra("dateStart", 0L),
            intent.getLongExtra("dateFinish", 0L),
            name,
            description
        )

        secondViewModel.date.observe(this) { date ->
            date?.let {
                dateTextView.text = "Дата: ${dateFormat.format(it)}"
            }
        }
        llBtnChangeDate.setOnClickListener {

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Выберите дату")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()
            datePicker.show(supportFragmentManager, "datePicker")

            datePicker.addOnPositiveButtonClickListener { selection ->
                secondViewModel.setDate(selection)
            }
        }

        secondViewModel.time.observe(this, { time ->
            time?.let {
                timeTextView.text = String.format(
                    "Время: %02d:00 - %02d:00", it, (it + 1) % 24
                )
            }
        })

        llBtnChangeTime.setOnClickListener {
            val picker =
                MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(12)
                    .setMinute(0).setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                    .setTitleText("Выберите время").build()
            picker.show(supportFragmentManager, "TIME_PICKER")

            picker.addOnPositiveButtonClickListener {
                secondViewModel.setTime(picker.hour)
                picker.dismiss()
            }
        }

        mainHeader.doAfterTextChanged { text ->
            secondViewModel.setHeader(text.toString())
        }

        etDescription.doAfterTextChanged { text ->
            secondViewModel.setDescription(text.toString())
        }


        btnBack.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        btnDelete.setOnClickListener {
            secondViewModel.deleteNote()
            setResult(RESULT_OK)
            finish()
        }
        btnSave.setOnClickListener {
            if (isEditing) {
                secondViewModel.updateNote()
            } else {
                if (etDescription.text.toString().isNotBlank() || mainHeader.text.toString()
                        .isNotBlank()
                ) {
                    secondViewModel.insertNewNote()
                }
            }
            setResult(RESULT_OK)
            finish()
        }
    }


}

package com.example.daybookv2.app.presentation.Adapter

import android.provider.ContactsContract.CommonDataKinds.Note
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.daybookv2.databinding.NoteItemBinding
import com.example.daybookv2.domain.model.NoteModel
import java.text.SimpleDateFormat
import java.util.Locale


class AdapterNotes(
    private val listNote: MutableList<NoteModel>,
    private val onNoteSelected: (NoteModel) -> Unit
): RecyclerView.Adapter<AdapterNotes.NotesViewHolder>(){
    class NotesViewHolder(val binding: NoteItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
        ): NotesViewHolder {
        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotesViewHolder(binding)
    }

    override fun getItemCount(): Int = listNote.size

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val note = listNote[position]
        with(holder.binding){
            eventName.text = note.name
            eventDate.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(note.dateStart)
            root.setOnClickListener{
                onNoteSelected(note)
            }
        }
    }

    fun updateData(newNotes: List<NoteModel>) {
        listNote.clear()
        listNote.addAll(newNotes)
        notifyDataSetChanged()
    }


}
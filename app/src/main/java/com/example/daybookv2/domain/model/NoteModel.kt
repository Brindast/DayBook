package com.example.daybookv2.domain.model

data class NoteModel(
    val id: Int,
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
)
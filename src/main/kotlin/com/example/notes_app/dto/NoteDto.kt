package com.example.notes_app.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateNoteRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 300)
    val title: String,

    val content: String? = null,
    val tags: String? = null,
    val isFavorite: Boolean = false
)

data class UpdateNoteRequest(
    val title: String? = null,
    val content: String? = null,
    val tags: String? = null,
    val isFavorite: Boolean? = null
)

data class NoteResponse(
    val id: Long,
    val title: String,
    val content: String?,
    val tags: String?,
    val isFavorite: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val notebookId: Long,
    val notebookTitle: String
)
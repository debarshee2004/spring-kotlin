package com.example.notes_app.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

data class CreateNotebookRequest(
    @NotBlank(message = "Title is required")
    @Size(max = 200)
    val title: String,

    @Size(max = 500)
    val description: String? = null
)

data class UpdateNotebookRequest(
    val title: String? = null,
    val description: String? = null
)

data class NotebookResponse(
    val id: Long,
    val title: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val noteCount: Int = 0
)
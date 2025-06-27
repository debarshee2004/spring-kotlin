package com.example.notes_app.services

import com.example.notes_app.dto.CreateNotebookRequest
import com.example.notes_app.dto.NotebookResponse
import com.example.notes_app.dto.UpdateNotebookRequest
import com.example.notes_app.models.Notebook
import com.example.notes_app.repository.NotebookRepository
import com.example.notes_app.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotebookService(
    private val notebookRepository: NotebookRepository,
    private val userRepository: UserRepository
) {

    fun createNotebook(userId: Long, request: CreateNotebookRequest): NotebookResponse {
        val user = userRepository.findById(userId)
            .orElseThrow { NoSuchElementException("User not found with id: $userId") }

        val notebook = Notebook(
            title = request.title,
            description = request.description,
            user = user
        )

        val savedNotebook = notebookRepository.save(notebook)
        return mapToNotebookResponse(savedNotebook)
    }

    fun getNotebooksByUserId(userId: Long): List<NotebookResponse> {
        return notebookRepository.findByUserId(userId).map { mapToNotebookResponse(it) }
    }

    fun getNotebookById(userId: Long, notebookId: Long): NotebookResponse {
        val notebook = notebookRepository.findByUserIdAndId(userId, notebookId)
            ?: throw NoSuchElementException("Notebook not found")
        return mapToNotebookResponse(notebook)
    }

    fun updateNotebook(userId: Long, notebookId: Long, request: UpdateNotebookRequest): NotebookResponse {
        val notebook = notebookRepository.findByUserIdAndId(userId, notebookId)
            ?: throw NoSuchElementException("Notebook not found")

        val updatedNotebook = notebook.copy(
            title = request.title ?: notebook.title,
            description = request.description ?: notebook.description,
            updatedAt = LocalDateTime.now()
        )

        val savedNotebook = notebookRepository.save(updatedNotebook)
        return mapToNotebookResponse(savedNotebook)
    }

    fun deleteNotebook(userId: Long, notebookId: Long) {
        val notebook = notebookRepository.findByUserIdAndId(userId, notebookId)
            ?: throw NoSuchElementException("Notebook not found")
        notebookRepository.delete(notebook)
    }

    private fun mapToNotebookResponse(notebook: Notebook): NotebookResponse {
        return NotebookResponse(
            id = notebook.id,
            title = notebook.title,
            description = notebook.description,
            createdAt = notebook.createdAt,
            updatedAt = notebook.updatedAt,
            noteCount = notebook.notes.size
        )
    }
}
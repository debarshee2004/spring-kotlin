package com.example.notes_app.services

import com.example.notes_app.dto.CreateNoteRequest
import com.example.notes_app.dto.NoteResponse
import com.example.notes_app.dto.UpdateNoteRequest
import com.example.notes_app.models.Note
import com.example.notes_app.repository.NoteRepository
import com.example.notes_app.repository.NotebookRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NoteService(
    private val noteRepository: NoteRepository,
    private val notebookRepository: NotebookRepository
) {

    fun createNote(userId: Long, notebookId: Long, request: CreateNoteRequest): NoteResponse {
        val notebook = notebookRepository.findByUserIdAndId(userId, notebookId)
            ?: throw NoSuchElementException("Notebook not found")

        val note = Note(
            title = request.title,
            content = request.content,
            tags = request.tags,
            isFavorite = request.isFavorite,
            notebook = notebook
        )

        val savedNote = noteRepository.save(note)
        return mapToNoteResponse(savedNote)
    }

    fun getNotesByUserId(userId: Long): List<NoteResponse> {
        return noteRepository.findByNotebookUserId(userId).map { mapToNoteResponse(it) }
    }

    fun getNotesByNotebookId(notebookId: Long): List<NoteResponse> {
        return noteRepository.findByNotebookId(notebookId).map { mapToNoteResponse(it) }
    }

    fun getNoteById(userId: Long, noteId: Long): NoteResponse {
        val note = noteRepository.findByNotebookUserIdAndId(userId, noteId)
            ?: throw NoSuchElementException("Note not found")
        return mapToNoteResponse(note)
    }

    fun updateNote(userId: Long, noteId: Long, request: UpdateNoteRequest): NoteResponse {
        val note = noteRepository.findByNotebookUserIdAndId(userId, noteId)
            ?: throw NoSuchElementException("Note not found")

        val updatedNote = note.copy(
            title = request.title ?: note.title,
            content = request.content ?: note.content,
            tags = request.tags ?: note.tags,
            isFavorite = request.isFavorite ?: note.isFavorite,
            updatedAt = LocalDateTime.now()
        )

        val savedNote = noteRepository.save(updatedNote)
        return mapToNoteResponse(savedNote)
    }

    fun deleteNote(userId: Long, noteId: Long) {
        val note = noteRepository.findByNotebookUserIdAndId(userId, noteId)
            ?: throw NoSuchElementException("Note not found")
        noteRepository.delete(note)
    }

    fun searchNotes(query: String): List<NoteResponse> {
        return noteRepository.findByTitleContainingIgnoreCase(query).map { mapToNoteResponse(it) }
    }

    fun getFavoriteNotes(): List<NoteResponse> {
        return noteRepository.findByIsFavoriteTrue().map { mapToNoteResponse(it) }
    }

    private fun mapToNoteResponse(note: Note): NoteResponse {
        return NoteResponse(
            id = note.id,
            title = note.title,
            content = note.content,
            tags = note.tags,
            isFavorite = note.isFavorite,
            createdAt = note.createdAt,
            updatedAt = note.updatedAt,
            notebookId = note.notebook.id,
            notebookTitle = note.notebook.title
        )
    }
}
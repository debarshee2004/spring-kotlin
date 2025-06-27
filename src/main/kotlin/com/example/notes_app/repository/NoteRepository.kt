package com.example.notes_app.repository

import com.example.notes_app.models.Note
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Long> {
    fun findByNotebookId(notebookId: Long): List<Note>
    fun findByNotebookUserId(userId: Long): List<Note>
    fun findByNotebookUserIdAndId(userId: Long, noteId: Long): Note?
    fun findByIsFavoriteTrue(): List<Note>
    fun findByTitleContainingIgnoreCase(title: String): List<Note>
}
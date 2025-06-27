package com.example.notes_app.repository

import com.example.notes_app.models.Notebook
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotebookRepository : JpaRepository<Notebook, Long> {
    fun findByUserId(userId: Long): List<Notebook>
    fun findByUserIdAndId(userId: Long, notebookId: Long): Notebook?
}
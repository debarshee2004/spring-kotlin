package com.example.notes_app.controllers

import com.example.notes_app.dto.CreateNotebookRequest
import com.example.notes_app.dto.NotebookResponse
import com.example.notes_app.dto.UpdateNotebookRequest
import com.example.notes_app.services.NotebookService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users/{userId}/notebooks")
class NotebookController(private val notebookService: NotebookService) {

    @PostMapping
    fun createNotebook(
        @PathVariable userId: Long,
        @Valid @RequestBody request: CreateNotebookRequest
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.createNotebook(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(notebook)
    }

    @GetMapping
    fun getNotebooks(@PathVariable userId: Long): ResponseEntity<List<NotebookResponse>> {
        val notebooks = notebookService.getNotebooksByUserId(userId)
        return ResponseEntity.ok(notebooks)
    }

    @GetMapping("/{notebookId}")
    fun getNotebook(
        @PathVariable userId: Long,
        @PathVariable notebookId: Long
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.getNotebookById(userId, notebookId)
        return ResponseEntity.ok(notebook)
    }

    @PutMapping("/{notebookId}")
    fun updateNotebook(
        @PathVariable userId: Long,
        @PathVariable notebookId: Long,
        @Valid @RequestBody request: UpdateNotebookRequest
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.updateNotebook(userId, notebookId, request)
        return ResponseEntity.ok(notebook)
    }

    @DeleteMapping("/{notebookId}")
    fun deleteNotebook(
        @PathVariable userId: Long,
        @PathVariable notebookId: Long
    ): ResponseEntity<Void> {
        notebookService.deleteNotebook(userId, notebookId)
        return ResponseEntity.noContent().build()
    }
}
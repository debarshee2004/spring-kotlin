package com.example.notes_app.controllers

import com.example.notes_app.dto.CreateNoteRequest
import com.example.notes_app.dto.NoteResponse
import com.example.notes_app.dto.UpdateNoteRequest
import com.example.notes_app.services.NoteService
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
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class NoteController(private val noteService: NoteService) {

    @PostMapping("/users/{userId}/notebooks/{notebookId}/notes")
    fun createNote(
        @PathVariable userId: Long,
        @PathVariable notebookId: Long,
        @Valid @RequestBody request: CreateNoteRequest
    ): ResponseEntity<NoteResponse> {
        val note = noteService.createNote(userId, notebookId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(note)
    }

    @GetMapping("/users/{userId}/notes")
    fun getNotesByUser(@PathVariable userId: Long): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getNotesByUserId(userId)
        return ResponseEntity.ok(notes)
    }

    @GetMapping("/notebooks/{notebookId}/notes")
    fun getNotesByNotebook(@PathVariable notebookId: Long): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getNotesByNotebookId(notebookId)
        return ResponseEntity.ok(notes)
    }

    @GetMapping("/users/{userId}/notes/{noteId}")
    fun getNote(
        @PathVariable userId: Long,
        @PathVariable noteId: Long
    ): ResponseEntity<NoteResponse> {
        val note = noteService.getNoteById(userId, noteId)
        return ResponseEntity.ok(note)
    }

    @PutMapping("/users/{userId}/notes/{noteId}")
    fun updateNote(
        @PathVariable userId: Long,
        @PathVariable noteId: Long,
        @Valid @RequestBody request: UpdateNoteRequest
    ): ResponseEntity<NoteResponse> {
        val note = noteService.updateNote(userId, noteId, request)
        return ResponseEntity.ok(note)
    }

    @DeleteMapping("/users/{userId}/notes/{noteId}")
    fun deleteNote(
        @PathVariable userId: Long,
        @PathVariable noteId: Long
    ): ResponseEntity<Void> {
        noteService.deleteNote(userId, noteId)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/notes/search")
    fun searchNotes(@RequestParam query: String): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.searchNotes(query)
        return ResponseEntity.ok(notes)
    }

    @GetMapping("/notes/favorites")
    fun getFavoriteNotes(): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getFavoriteNotes()
        return ResponseEntity.ok(notes)
    }
}
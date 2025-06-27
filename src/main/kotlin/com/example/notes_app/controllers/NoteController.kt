package com.example.notes_app.controllers

import com.example.notes_app.dto.CreateNoteRequest
import com.example.notes_app.dto.NoteResponse
import com.example.notes_app.dto.UpdateNoteRequest
import com.example.notes_app.services.NoteService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
@RequestMapping("/api")
@Tag(name = "Notes", description = "Note management operations")
class NoteController(private val noteService: NoteService) {

    @Operation(
        summary = "Create a new note",
        description = "Creates a new note in the specified notebook"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Note created successfully",
            content = [Content(schema = Schema(implementation = NoteResponse::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        ),
        ApiResponse(
            responseCode = "404",
            description = "User or notebook not found"
        )
    ])
    @PostMapping("/users/{userId}/notebooks/{notebookId}/notes")
    fun createNote(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Notebook ID", required = true)
        @PathVariable notebookId: Long,
        @Valid @RequestBody request: CreateNoteRequest
    ): ResponseEntity<NoteResponse> {
        val note = noteService.createNote(userId, notebookId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(note)
    }

    @Operation(
        summary = "Get user's notes",
        description = "Retrieves all notes belonging to the specified user across all notebooks"
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of notes retrieved successfully"
    )
    @GetMapping("/users/{userId}/notes")
    fun getNotesByUser(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long
    ): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getNotesByUserId(userId)
        return ResponseEntity.ok(notes)
    }

    @Operation(
        summary = "Get notebook's notes",
        description = "Retrieves all notes in the specified notebook"
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of notes retrieved successfully"
    )
    @GetMapping("/notebooks/{notebookId}/notes")
    fun getNotesByNotebook(
        @Parameter(description = "Notebook ID", required = true)
        @PathVariable notebookId: Long
    ): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getNotesByNotebookId(notebookId)
        return ResponseEntity.ok(notes)
    }

    @Operation(
        summary = "Get note by ID",
        description = "Retrieves a specific note belonging to the user"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Note found",
            content = [Content(schema = Schema(implementation = NoteResponse::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Note not found"
        )
    ])
    @GetMapping("/users/{userId}/notes/{noteId}")
    fun getNote(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Note ID", required = true)
        @PathVariable noteId: Long
    ): ResponseEntity<NoteResponse> {
        val note = noteService.getNoteById(userId, noteId)
        return ResponseEntity.ok(note)
    }

    @Operation(
        summary = "Update note",
        description = "Updates note content, title, tags, or favorite status"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Note updated successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Note not found"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        )
    ])
    @PutMapping("/users/{userId}/notes/{noteId}")
    fun updateNote(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Note ID", required = true)
        @PathVariable noteId: Long,
        @Valid @RequestBody request: UpdateNoteRequest
    ): ResponseEntity<NoteResponse> {
        val note = noteService.updateNote(userId, noteId, request)
        return ResponseEntity.ok(note)
    }

    @Operation(
        summary = "Delete note",
        description = "Deletes the specified note"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = "Note deleted successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Note not found"
        )
    ])
    @DeleteMapping("/users/{userId}/notes/{noteId}")
    fun deleteNote(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Note ID", required = true)
        @PathVariable noteId: Long
    ): ResponseEntity<Void> {
        noteService.deleteNote(userId, noteId)
        return ResponseEntity.noContent().build()
    }

    @Operation(
        summary = "Search notes",
        description = "Search for notes by title (case-insensitive)"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Search results retrieved successfully"
    )
    @GetMapping("/notes/search")
    fun searchNotes(
        @Parameter(description = "Search query for note titles", required = true)
        @RequestParam query: String
    ): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.searchNotes(query)
        return ResponseEntity.ok(notes)
    }

    @Operation(
        summary = "Get favorite notes",
        description = "Retrieves all notes marked as favorites"
    )
    @ApiResponse(
        responseCode = "200",
        description = "Favorite notes retrieved successfully"
    )
    @GetMapping("/notes/favorites")
    fun getFavoriteNotes(): ResponseEntity<List<NoteResponse>> {
        val notes = noteService.getFavoriteNotes()
        return ResponseEntity.ok(notes)
    }
}
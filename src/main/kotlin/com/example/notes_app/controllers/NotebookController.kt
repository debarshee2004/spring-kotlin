package com.example.notes_app.controllers

import com.example.notes_app.dto.CreateNotebookRequest
import com.example.notes_app.dto.NotebookResponse
import com.example.notes_app.dto.UpdateNotebookRequest
import com.example.notes_app.services.NotebookService
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users/{userId}/notebooks")
@Tag(name = "Notebooks", description = "Notebook management operations")
class NotebookController(private val notebookService: NotebookService) {

    @Operation(
        summary = "Create a new notebook",
        description = "Creates a new notebook for the specified user"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "Notebook created successfully",
            content = [Content(schema = Schema(implementation = NotebookResponse::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        ),
        ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    ])
    @PostMapping
    fun createNotebook(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Valid @RequestBody request: CreateNotebookRequest
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.createNotebook(userId, request)
        return ResponseEntity.status(HttpStatus.CREATED).body(notebook)
    }

    @Operation(
        summary = "Get user's notebooks",
        description = "Retrieves all notebooks belonging to the specified user"
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of notebooks retrieved successfully"
    )
    @GetMapping
    fun getNotebooks(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long
    ): ResponseEntity<List<NotebookResponse>> {
        val notebooks = notebookService.getNotebooksByUserId(userId)
        return ResponseEntity.ok(notebooks)
    }

    @Operation(
        summary = "Get notebook by ID",
        description = "Retrieves a specific notebook belonging to the user"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Notebook found",
            content = [Content(schema = Schema(implementation = NotebookResponse::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "Notebook not found"
        )
    ])
    @GetMapping("/{notebookId}")
    fun getNotebook(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Notebook ID", required = true)
        @PathVariable notebookId: Long
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.getNotebookById(userId, notebookId)
        return ResponseEntity.ok(notebook)
    }

    @Operation(
        summary = "Update notebook",
        description = "Updates notebook title and/or description"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "Notebook updated successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Notebook not found"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        )
    ])
    @PutMapping("/{notebookId}")
    fun updateNotebook(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Notebook ID", required = true)
        @PathVariable notebookId: Long,
        @Valid @RequestBody request: UpdateNotebookRequest
    ): ResponseEntity<NotebookResponse> {
        val notebook = notebookService.updateNotebook(userId, notebookId, request)
        return ResponseEntity.ok(notebook)
    }

    @Operation(
        summary = "Delete notebook",
        description = "Deletes a notebook and all its associated notes"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = "Notebook deleted successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "Notebook not found"
        )
    ])
    @DeleteMapping("/{notebookId}")
    fun deleteNotebook(
        @Parameter(description = "User ID", required = true)
        @PathVariable userId: Long,
        @Parameter(description = "Notebook ID", required = true)
        @PathVariable notebookId: Long
    ): ResponseEntity<Void> {
        notebookService.deleteNotebook(userId, notebookId)
        return ResponseEntity.noContent().build()
    }
}
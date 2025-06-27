package com.example.notes_app.controllers

import com.example.notes_app.dto.CreateUserRequest
import com.example.notes_app.dto.UpdateUserRequest
import com.example.notes_app.dto.UserResponse
import com.example.notes_app.services.UserService
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
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User management operations")
class UserController(private val userService: UserService) {

    @Operation(
        summary = "Create a new user",
        description = "Creates a new user account with username, email, and password"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "201",
            description = "User created successfully",
            content = [Content(schema = Schema(implementation = UserResponse::class))]
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data or user already exists"
        )
    ])
    @PostMapping
    fun createUser(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        val user = userService.createUser(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(user)
    }

    @Operation(
        summary = "Get all users",
        description = "Retrieves a list of all registered users"
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of users retrieved successfully"
    )
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserResponse>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

    @Operation(
        summary = "Get user by ID",
        description = "Retrieves a specific user by their ID"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "User found",
            content = [Content(schema = Schema(implementation = UserResponse::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    ])
    @GetMapping("/{id}")
    fun getUserById(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id)
        return ResponseEntity.ok(user)
    }

    @Operation(
        summary = "Update user information",
        description = "Updates user's personal information (firstName, lastName, email)"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "200",
            description = "User updated successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "User not found"
        ),
        ApiResponse(
            responseCode = "400",
            description = "Invalid input data"
        )
    ])
    @PutMapping("/{id}")
    fun updateUser(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Long,
        @Valid @RequestBody request: UpdateUserRequest
    ): ResponseEntity<UserResponse> {
        val user = userService.updateUser(id, request)
        return ResponseEntity.ok(user)
    }

    @Operation(
        summary = "Delete user",
        description = "Deletes a user and all associated notebooks and notes"
    )
    @ApiResponses(value = [
        ApiResponse(
            responseCode = "204",
            description = "User deleted successfully"
        ),
        ApiResponse(
            responseCode = "404",
            description = "User not found"
        )
    ])
    @DeleteMapping("/{id}")
    fun deleteUser(
        @Parameter(description = "User ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}

package com.example.notes_app.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@Schema(description = "Request payload for creating a new user")
data class CreateUserRequest(
    @Schema(description = "Unique username for the user", example = "john_doe", required = true)
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    val username: String,

    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    val email: String,

    @Schema(description = "User's password (minimum 6 characters)", example = "securePassword123", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 6)
    val password: String,

    @Schema(description = "User's first name", example = "John")
    val firstName: String? = null,

    @Schema(description = "User's last name", example = "Doe")
    val lastName: String? = null
)

data class UpdateUserRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null
)

data class UserResponse(
    val id: Long,
    val username: String,
    val email: String,
    val firstName: String?,
    val lastName: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)
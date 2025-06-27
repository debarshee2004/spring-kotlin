package com.example.notes_app.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDateTime


data class CreateUserRequest(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    val username: String,

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    val email: String,

    @NotBlank(message = "Password is required")
    @Size(min = 6)
    val password: String,

    val firstName: String? = null,
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
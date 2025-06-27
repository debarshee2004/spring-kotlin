package com.example.notes_app.services

import com.example.notes_app.dto.CreateUserRequest
import com.example.notes_app.dto.UpdateUserRequest
import com.example.notes_app.dto.UserResponse
import com.example.notes_app.models.User
import com.example.notes_app.repository.UserRepository
import com.example.notes_app.utitls.PasswordUtil
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(private val userRepository: UserRepository) {

    fun createUser(request: CreateUserRequest): UserResponse {
        if (userRepository.existsByUsername(request.username)) {
            throw IllegalArgumentException("Username already exists")
        }
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("Email already exists")
        }

        val user = User(
            username = request.username,
            email = request.email,
            password = PasswordUtil.hashPassword(request.password),
            firstName = request.firstName,
            lastName = request.lastName
        )

        val savedUser = userRepository.save(user)
        return mapToUserResponse(savedUser)
    }

    fun getUserById(id: Long): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }
        return mapToUserResponse(user)
    }

    fun getAllUsers(): List<UserResponse> {
        return userRepository.findAll().map { mapToUserResponse(it) }
    }

    fun updateUser(id: Long, request: UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id)
            .orElseThrow { NoSuchElementException("User not found with id: $id") }

        request.email?.let { email ->
            if (userRepository.existsByEmail(email) && user.email != email) {
                throw IllegalArgumentException("Email already exists")
            }
        }

        val updatedUser = user.copy(
            firstName = request.firstName ?: user.firstName,
            lastName = request.lastName ?: user.lastName,
            email = request.email ?: user.email,
            updatedAt = LocalDateTime.now()
        )

        val savedUser = userRepository.save(updatedUser)
        return mapToUserResponse(savedUser)
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NoSuchElementException("User not found with id: $id")
        }
        userRepository.deleteById(id)
    }

    private fun mapToUserResponse(user: User): UserResponse {
        return UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            firstName = user.firstName,
            lastName = user.lastName,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
    }
}
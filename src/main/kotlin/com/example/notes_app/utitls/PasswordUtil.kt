package com.example.notes_app.utitls

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

object PasswordUtil {
    private val passwordEncoder: PasswordEncoder = BCryptPasswordEncoder()

    /**
     * Hash a plain text password
     */
    fun hashPassword(plainPassword: String): String {
        return passwordEncoder.encode(plainPassword)
    }

    /**
     * Verify if a plain text password matches the hashed password
     */
    fun verifyPassword(plainPassword: String, hashedPassword: String): Boolean {
        return passwordEncoder.matches(plainPassword, hashedPassword)
    }
}
package com.example.notes_app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NotesAppApplication

fun main(args: Array<String>) {
	runApplication<NotesAppApplication>(*args)
}

package com.example.notes_app.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Note App API")
                    .description("A comprehensive REST API using Kotlin and Spring Boot for managing users, notebooks, and notes")
                    .version("0.0.1")
                    .license(
                        License()
                            .name("MIT License")
                            .url("https://opensource.org/licenses/MIT")
                    )
            )
            .servers(
                listOf(
                    Server()
                        .url("http://localhost:8080")
                        .description("Development server"),
                    Server()
                        .url("https://api.noteapp.com")
                        .description("Production server")
                )
            )
    }
}
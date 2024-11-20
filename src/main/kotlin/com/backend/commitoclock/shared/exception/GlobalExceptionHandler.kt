package com.backend.commitoclock.shared.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CommitOClockException::class)
    fun handleCommitOClockException(ex: CommitOClockException): ResponseEntity<ErrorResponse> =
        ErrorResponse(status = ex.status.value(), message = ex.message ?: "Unexpected error")
        .let {
            ResponseEntity(it, ex.status)
        }
}

data class ErrorResponse(
    val status: Int,
    val message: String
)

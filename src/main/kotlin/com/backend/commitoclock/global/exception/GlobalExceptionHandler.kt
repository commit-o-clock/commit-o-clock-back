package com.backend.commitoclock.global.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(CommitOClockException::class)
    fun handleCommitOClockException(ex: CommitOClockException): ResponseEntity<ErrorResponse> {
        val response =
            ErrorResponse(status = ex.status.value(), message = ex.message ?: "Unexpected error")
        return ResponseEntity(response, ex.status)  // status 값을 HTTP 응답 코드로 사용
    }
}

data class ErrorResponse(
    val status: Int,
    val message: String
)

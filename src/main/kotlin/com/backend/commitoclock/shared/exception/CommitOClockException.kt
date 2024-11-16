package com.backend.commitoclock.shared.exception

import org.springframework.http.HttpStatus

open class CommitOClockException(
    val status: HttpStatus,
    message: String
) : RuntimeException(message)

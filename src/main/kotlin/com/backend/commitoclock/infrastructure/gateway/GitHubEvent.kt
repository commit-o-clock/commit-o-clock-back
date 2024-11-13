package com.backend.commitoclock.infrastructure.gateway

import java.time.LocalDateTime

data class GitHubEvent(
    val type: String,
    val createdAt: LocalDateTime
)
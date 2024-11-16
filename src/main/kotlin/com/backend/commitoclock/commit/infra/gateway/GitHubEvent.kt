package com.backend.commitoclock.commit.infra.gateway

import java.time.LocalDateTime

data class GitHubEvent(
    val type: String,
    val createdAt: LocalDateTime
)
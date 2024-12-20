package com.backend.commitoclock.commit.domain.model

data class Commit(
    val id: String?,
    val userId: String,
    val githubId: String,
    val commitDate: String,
    val commitCount: Int,
) {
}
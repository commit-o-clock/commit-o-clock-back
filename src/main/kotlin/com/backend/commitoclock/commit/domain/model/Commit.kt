package com.backend.commitoclock.commit.domain.model

class Commit(
    val id: String?,
    val userId: String,
    val githubId: String,
    val commitDate: String,
    val commitCount: Int,
    var isNotified: Boolean
) {
    fun makeNotified() {
        this.isNotified = true
    }
}
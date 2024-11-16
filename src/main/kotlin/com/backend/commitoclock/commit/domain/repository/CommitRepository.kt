package com.backend.commitoclock.commit.domain.repository

import com.backend.commitoclock.commit.domain.model.Commit

interface CommitRepository {
    fun save(commit: Commit): Commit
    fun findByUserIdAndCommitDateAndIsNotified(
        date: String,
        isNotified: Boolean
    ): List<Commit>
    fun updateAll(targetCommits: List<Commit>)
}
package com.backend.commitoclock.commit.domain.repository

import com.backend.commitoclock.commit.domain.model.Commit

interface CommitRepository {
    fun save(commit: Commit): Commit
    fun findByCommitDate(date: String): List<Commit>
    fun saveAll(commits: List<Commit>)
}
package com.backend.commitoclock.commit.service

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import org.springframework.stereotype.Service

@Service
class CommitValidateService(
    private val commitRepository: CommitRepository
) {
    fun validate(
        commits: List<Commit>,
        commitId: String
    ): Boolean {
        return commits.any { it.id == commitId }
    }
}

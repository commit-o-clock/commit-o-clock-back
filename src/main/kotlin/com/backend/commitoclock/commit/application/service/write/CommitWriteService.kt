package com.backend.commitoclock.commit.application.service.write

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommitWriteService(
    private val commitRepository: CommitRepository,
) {
    @Transactional
    fun saveAll(commits: List<Commit>) {
        if (commits.isEmpty()) return
        commitRepository.saveAll(commits)
    }
}

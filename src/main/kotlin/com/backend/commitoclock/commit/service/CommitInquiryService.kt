package com.backend.commitoclock.commit.service

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import org.springframework.stereotype.Service

@Service
class CommitInquiryService(
    private val commitRepository: CommitRepository
) {
    fun getCommitsOfToday(
        date: String
    ): List<Commit> {
        return commitRepository.findByCommitDate(date)
    }
}

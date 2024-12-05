package com.backend.commitoclock.commit.application.service.read

import com.backend.commitoclock.commit.domain.repository.CommitRepository
import org.springframework.stereotype.Service

@Service
class CommitReadService(
    private val commitRepository: CommitRepository,
) {
}

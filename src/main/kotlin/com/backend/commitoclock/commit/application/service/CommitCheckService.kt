package com.backend.commitoclock.commit.application.service

import com.backend.commitoclock.commit.infra.gateway.CommitGateway
import org.springframework.stereotype.Service

@Service
class CommitCheckService(
    private val commitGateway: CommitGateway
) {
    fun hasCommitted(
        githubId: String,
        date: String
    ): Int {
        return commitGateway.fetchCommitData(githubId, date)
    }
}
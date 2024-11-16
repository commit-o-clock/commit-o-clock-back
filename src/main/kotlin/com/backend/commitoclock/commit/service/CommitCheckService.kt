package com.backend.commitoclock.commit.service

import com.backend.commitoclock.commit.infra.gateway.CommitGateway
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

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
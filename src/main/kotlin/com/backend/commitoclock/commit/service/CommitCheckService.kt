package com.backend.commitoclock.commit.service

import com.backend.commitoclock.commit.infra.gateway.CommitGateway
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class CommitCheckService(
    private val commitGateway: CommitGateway
) {
    fun hasCommittedToday(githubId: String): Int {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        return commitGateway.fetchCommitData(githubId, today)
    }
}
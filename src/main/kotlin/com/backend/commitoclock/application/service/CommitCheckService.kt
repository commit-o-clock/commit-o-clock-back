package com.backend.commitoclock.application.service

import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.infrastructure.gateway.CommitGateway
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class CommitCheckService(
    private val commitGateway: CommitGateway
) {
    fun hasCommittedToday(githubId: String): Int {
        val todayDate = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val commitData = commitGateway.fetchCommitData(githubId)
        val todayCommitCount = commitData[todayDate] ?: 0
        return todayCommitCount
    }
}
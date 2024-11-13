package com.backend.commitoclock.application.service

import com.backend.commitoclock.infrastructure.gateway.CommitGateway
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CommitCheckService(
    private val githubGateway: CommitGateway,
) {
    fun hasUserCommittedToday(githubId: String): Boolean {
        val today = LocalDate.now()
        val events = githubGateway.retrieveEvents(githubId, today)
        return events.any { event ->
            event.type == "PushEvent" && event.createdAt.toLocalDate() == today
        }
    }
}
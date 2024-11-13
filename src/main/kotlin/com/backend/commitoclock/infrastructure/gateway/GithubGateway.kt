package com.backend.commitoclock.infrastructure.gateway

import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.LocalDate

@Component
class CommitGateway(
    private val restClient: RestClient
) {
    fun retrieveEvents(
        username: String,
        date: LocalDate
    ): Array<GitHubEvent> {
        return restClient.get()
            .uri("https://api.github.com/users/${username}/events/public")
            .retrieve()
            .toEntity(Array<GitHubEvent>::class.java)
            .body ?: emptyArray()
    }
}

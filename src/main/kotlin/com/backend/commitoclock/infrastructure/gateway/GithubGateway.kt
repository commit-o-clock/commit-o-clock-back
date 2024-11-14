package com.backend.commitoclock.infrastructure.gateway

import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class CommitGateway {
    fun fetchCommitData(username: String): Map<String, Int> {
        val url = "https://github.com/users/$username/contributions"
        val doc = Jsoup.connect(url).get()
        return doc.select("rect").associate {
            val date = it.attr("data-date")
            val count = it.attr("data-count").toIntOrNull() ?: 0
            date to count
        }
    }
}

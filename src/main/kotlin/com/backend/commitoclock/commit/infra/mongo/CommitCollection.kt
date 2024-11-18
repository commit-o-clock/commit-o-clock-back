package com.backend.commitoclock.commit.infra.mongo

import com.backend.commitoclock.commit.domain.model.Commit
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Document(collection = "commits")
data class CommitCollection(
    val id: String? = null,
    val userId: String,
    val githubId: String,
    val commitDate: String,
    val commitCount: Int,
    val createdAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
) {
    companion object {
        fun from(domain: Commit): CommitCollection {
            return CommitCollection(
                id = domain.id,
                userId = domain.userId,
                githubId = domain.githubId,
                commitDate = domain.commitDate,
                commitCount = domain.commitCount,
            )
        }
    }

    fun toDomain(): Commit {
        return Commit(
            id = id,
            userId = userId,
            githubId = githubId,
            commitDate = commitDate,
            commitCount = commitCount,
        )
    }
}
package com.backend.commitoclock.commit.infra.mongo

import com.backend.commitoclock.commit.domain.model.Commit
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "commits")
data class CommitCollection(
    val id: String? = null,
    val userId: String,
    val githubId: String,
    val commitDate: String,
    val commitCount: Int,
    val isNotified: Boolean,
    val createdAt: String = LocalDateTime.now().toString(),
) {
    companion object {
        fun from(domain: Commit): CommitCollection {
            return CommitCollection(
                id = domain.id,
                userId = domain.userId,
                githubId = domain.githubId,
                commitDate = domain.commitDate,
                commitCount = domain.commitCount,
                isNotified = domain.isNotified,
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
            isNotified = isNotified,
        )
    }
}
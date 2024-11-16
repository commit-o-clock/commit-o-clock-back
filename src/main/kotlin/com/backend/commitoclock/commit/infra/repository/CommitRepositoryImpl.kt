package com.backend.commitoclock.commit.infra.repository

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import com.backend.commitoclock.commit.infra.mongo.CommitCollection
import com.backend.commitoclock.commit.infra.mongo.CommitMongoRepository
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CommitRepositoryImpl(
    private val commitMongoRepository: CommitMongoRepository,
    private val mongoTemplate: MongoTemplate
) : CommitRepository {
    override fun save(commit: Commit): Commit {
        return commitMongoRepository
            .save(CommitCollection.from(commit))
            .toDomain()
    }

    override fun findByUserIdAndCommitDateAndIsNotified(
        date: String,
        isNotified: Boolean
    ): List<Commit> {
        return commitMongoRepository
            .findByCommitDateAndIsNotified(date, isNotified)
            .map { it.toDomain() }
    }

    override fun updateAll(targetCommits: List<Commit>) {
        val bulkOps = mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED,
            CommitCollection::class.java
        )
        targetCommits.forEach { commit ->
            val query = Query(Criteria.where("id").`is`(commit.id))
            val update = Update()
                .set("userId", commit.userId)
                .set("githubId", commit.githubId)
                .set("commitDate", commit.commitDate)
                .set("commitCount", commit.commitCount)
                .set("isNotified", commit.isNotified)

            bulkOps.upsert(query, update)
        }
        bulkOps.execute()
    }

}

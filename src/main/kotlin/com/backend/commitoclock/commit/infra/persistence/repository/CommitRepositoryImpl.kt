package com.backend.commitoclock.commit.infra.persistence.repository

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import com.backend.commitoclock.commit.infra.persistence.mongo.CommitCollection
import com.backend.commitoclock.commit.infra.persistence.mongo.CommitMongoRepository
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

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

    override fun findByCommitDate(date: String): List<Commit> {
        return commitMongoRepository
            .findByCommitDate(date)
            .map { it.toDomain() }
    }

    override fun saveAll(commits: List<Commit>) {
        val bulkOps = mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED,
            CommitCollection::class.java
        )
        commits.forEach { commit -> bulkOps.insert(CommitCollection.from(commit)) }
        bulkOps.execute()
    }

}

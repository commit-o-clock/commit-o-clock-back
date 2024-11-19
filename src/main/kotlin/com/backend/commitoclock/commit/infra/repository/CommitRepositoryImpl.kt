package com.backend.commitoclock.commit.infra.repository

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import com.backend.commitoclock.commit.infra.mongo.CommitCollection
import com.backend.commitoclock.commit.infra.mongo.CommitMongoRepository
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class CommitRepositoryImpl(
    private val commitMongoRepository: CommitMongoRepository,
    private val mongoTemplate: MongoTemplate
) : CommitRepository {

    @Transactional
    override fun save(commit: Commit): Commit {
        return commitMongoRepository
            .save(CommitCollection.from(commit))
            .toDomain()
    }

    @Transactional(readOnly = true)
    override fun findByCommitDate(date: String): List<Commit> {
        return commitMongoRepository
            .findByCommitDate(date)
            .map { it.toDomain() }
    }

    @Transactional
    override fun saveAll(commits: List<Commit>) {
        val bulkOps = mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED,
            CommitCollection::class.java
        )
        commits.forEach { commit -> bulkOps.insert(CommitCollection.from(commit)) }
        bulkOps.execute()
    }

}

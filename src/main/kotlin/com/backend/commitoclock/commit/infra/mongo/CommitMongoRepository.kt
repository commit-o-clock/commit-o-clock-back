package com.backend.commitoclock.commit.infra.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface CommitMongoRepository : MongoRepository<CommitCollection, String> {
    fun findByCommitDate(date: String): List<CommitCollection>
}
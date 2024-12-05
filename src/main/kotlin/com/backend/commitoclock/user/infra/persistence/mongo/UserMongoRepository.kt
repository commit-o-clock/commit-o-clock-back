package com.backend.commitoclock.user.infra.persistence.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.util.*

@Repository
interface UserMongoRepository : MongoRepository<UserCollection, String> {
    fun findByUsername(username: String): Optional<UserCollection>
    fun findByGithubId(githubId: String): Optional<UserCollection>
    fun findByLastCommitDateAfter(date: LocalDateTime): List<UserCollection>
    fun existsByGithubId(githubId: String): Boolean
    fun findAllByNotificationPreferences_PreferredTime(currentHour: Int): List<UserCollection>
}

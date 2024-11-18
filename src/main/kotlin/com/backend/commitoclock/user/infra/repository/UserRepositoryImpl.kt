package com.backend.commitoclock.user.infra.repository

import com.backend.commitoclock.shared.exception.CommitOClockException
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.user.infra.mongo.NotificationPreference
import com.backend.commitoclock.user.infra.mongo.UserCollection
import com.backend.commitoclock.user.infra.mongo.UserMongoRepository
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val userMongoRepository: UserMongoRepository,
    private val mongoTemplate: MongoTemplate
) : UserRepository {

    @Transactional(readOnly = true)
    override fun findAll(): List<User> {
        return userMongoRepository.findAll().map { it.toDomain() }
    }

    @Transactional(readOnly = true)
    override fun findById(userId: String): User? {
        return userMongoRepository
            .findById(userId)
            .map { it.toDomain() }
            .orElse(null)
    }

    @Transactional(readOnly = true)
    override fun findByUsername(username: String): User {
        return userMongoRepository
            .findByUsername(username)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    @Transactional(readOnly = true)
    override fun findByGithubId(
        githubId: String
    ): User {
        return userMongoRepository
            .findByGithubId(githubId)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    @Transactional(readOnly = true)
    override fun findByLastCommitDateAfter(
        date: LocalDateTime
    ): List<User> {
        val userCollections = userMongoRepository.findByLastCommitDateAfter(date)
        return userCollections.map { it.toDomain() }
    }

    @Transactional
    override fun save(user: User): User {
        UserCollection(
            id = user.id,
            username = user.username,
            githubId = user.githubId,
            lastCommitDate = user.lastCommitDate,
            notificationPreferences =
            NotificationPreference(
                enableDailyReminder = user.notificationPreferences.enableDailyReminder,
                preferredTime = user.notificationPreferences.preferredTime,
                phoneNumber = user.notificationPreferences.phoneNumber,
                socialMediaId = user.notificationPreferences.socialMediaId,
                notificationMethod = user.notificationPreferences.notificationMethod
            )
        ).let {
            return userMongoRepository.save(it).toDomain()
        }
    }

    @Transactional(readOnly = true)
    override fun isExist(githubId: String): Boolean {
        return userMongoRepository.existsByGithubId(githubId)
    }

    @Transactional(readOnly = true)
    override fun findAllByPreferredTime(currentHour: Int): List<User> {
        return userMongoRepository
            .findAllByNotificationPreferences_PreferredTime(currentHour)
            .map { it.toDomain() }
    }

    @Transactional
    override fun saveAll(users: List<User>) {
        val bulkOps = mongoTemplate.bulkOps(
            org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED,
            UserCollection::class.java
        )
        users.forEach { user -> bulkOps.insert(UserCollection.from(user)) }
        bulkOps.execute()
    }

}
package com.backend.commitoclock.infrastructure.repository

import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.domain.repository.UserRepository
import com.backend.commitoclock.global.exception.CommitOClockException
import com.backend.commitoclock.infrastructure.mongo.model.NotificationPreference
import com.backend.commitoclock.infrastructure.mongo.model.UserCollection
import com.backend.commitoclock.infrastructure.mongo.repository.UserMongoRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class UserRepositoryImpl(
    private val userMongoRepository: UserMongoRepository
) : UserRepository {

    override fun findAll(): List<User> {
        return userMongoRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: String): User {
        return userMongoRepository
            .findById(id)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    override fun findByUsername(username: String): User {
        return userMongoRepository
            .findByUsername(username)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    override fun findByGithubId(
        githubId: String
    ): User {
        return userMongoRepository
            .findByGithubId(githubId)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    override fun findByLastCommitDateAfter(
        date: LocalDateTime
    ): List<User> {
        val userCollections = userMongoRepository.findByLastCommitDateAfter(date)
        return userCollections.map { it.toDomain() }
    }

    override fun save(user: User): User {
        UserCollection(
            id = user.id,
            username = user.username,
            githubId = user.githubId,
            lastCommitDate = user.lastCommitDate,
            isNotified = user.isNotified,
            notificationPreferences =
            NotificationPreference(
                enableDailyReminder = user.notificationPreferences.enableDailyReminder,
                preferredTime = user.notificationPreferences.preferredTime,
                notificationMethod = user.notificationPreferences.notificationMethod
            )
        ).let {
            return userMongoRepository.save(it).toDomain()
        }
    }
}
package com.backend.commitoclock.infrastructure.repository

import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.domain.repository.UserRepository
import com.backend.commitoclock.global.exception.CommitOClockException
import com.backend.commitoclock.infrastructure.mongo.repository.UserMongoRepository
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

class UserRepositoryImpl(
    private val userMongoRepository: UserMongoRepository
) : UserRepository {
    @Override
    fun findByUsername(username: String): User {
        return userMongoRepository
            .findByUsername(username)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    @Override
    fun findByGithubId(
        githubId: String
    ): User {
        return userMongoRepository
            .findByGithubId(githubId)
            .map { it.toDomain() }
            .orElseThrow { CommitOClockException(HttpStatus.NOT_FOUND, "User not found") }
    }

    @Override
    fun findByLastCommitDateAfter(
        date: LocalDateTime
    ): List<User> {
        val userCollections = userMongoRepository.findByLastCommitDateAfter(date)
        return userCollections.map { it.toDomain() }
    }
}
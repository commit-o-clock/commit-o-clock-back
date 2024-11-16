package com.backend.commitoclock.user.domain.repository

import com.backend.commitoclock.user.domain.model.User
import java.time.LocalDateTime

interface UserRepository {
    fun findAll(): List<User>
    fun findById(userId: String): User?
    fun findByUsername(username: String): User
    fun findByGithubId(githubId: String): User
    fun findByLastCommitDateAfter(date: LocalDateTime): List<User>
    fun save(user: User): User
    fun isExist(githubId: String): Boolean
}
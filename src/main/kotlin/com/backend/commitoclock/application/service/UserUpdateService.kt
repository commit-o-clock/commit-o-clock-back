package com.backend.commitoclock.application.service

import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserUpdateService(
    private val userRepository: UserRepository
) {
    fun save(user: User) {
        userRepository.save(user)
    }
}
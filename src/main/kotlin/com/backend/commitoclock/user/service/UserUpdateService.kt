package com.backend.commitoclock.user.service

import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserUpdateService(
    private val userRepository: UserRepository
) {
    fun save(user: User) {
        userRepository.save(user)
    }
}
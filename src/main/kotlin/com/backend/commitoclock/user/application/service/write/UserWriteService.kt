package com.backend.commitoclock.user.application.service.write

import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserWriteService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun saveAll(users: List<User>) {
        if(users.isEmpty()) {
            return
        }
        userRepository.saveAll(users)
    }
}
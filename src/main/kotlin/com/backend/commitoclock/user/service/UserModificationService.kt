package com.backend.commitoclock.user.service

import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.user.service.command.UserModificationCommand
import org.springframework.stereotype.Service

@Service
class UserModificationService(
    private val userRepository: UserRepository
) {
    fun updateUser(
        userId: String,
        userModificationCommand: UserModificationCommand
    ) {
        userRepository.findById(userId)?.let {
            val updateFields = it.update(userModificationCommand)
            if (updateFields.isEmpty()) return
            if (it.id?.isEmpty() == true) return
            userRepository.updateFields(it.id!!, updateFields)
        }
    }
}

package com.backend.commitoclock.user.application.service

import com.backend.commitoclock.user.application.dto.command.UserModificationCommand
import com.backend.commitoclock.user.domain.repository.UserRepository
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

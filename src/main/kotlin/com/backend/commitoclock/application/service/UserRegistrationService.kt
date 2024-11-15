package com.backend.commitoclock.application.service

import com.backend.commitoclock.application.service.model.UserRegistrationCommand
import com.backend.commitoclock.domain.model.NotificationPreferenceDomain
import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.domain.repository.UserRepository
import com.backend.commitoclock.global.exception.CommitOClockException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
class UserRegistrationService(
    private val userRepository: UserRepository
) {
    @Transactional
    fun registerUser(
        command: UserRegistrationCommand
    ) {
        userValidation(command)
        userRepository.save(
            User(
                username = command.username,
                githubId = command.githubId,
                isNotified = command.isNotified,
                notificationPreferences = NotificationPreferenceDomain(
                    enableDailyReminder = command.enableDailyReminder,
                    preferredTime = command.preferredTime,
                    notificationMethod = command.notificationMethod
                )
            )
        )
    }

    private fun userValidation(
        command: UserRegistrationCommand
    ) {
        userRepository.findByGithubId(command.githubId)?.let {
            throw CommitOClockException(
                HttpStatus.BAD_REQUEST,
                "User with GitHub ID ${command.githubId} already exists."
            )
        }
    }
}

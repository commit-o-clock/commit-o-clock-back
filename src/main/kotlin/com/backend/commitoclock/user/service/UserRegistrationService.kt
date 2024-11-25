package com.backend.commitoclock.user.service

import com.backend.commitoclock.shared.exception.CommitOClockException
import com.backend.commitoclock.user.domain.model.NotificationPreferenceDomain
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.user.service.command.UserRegistrationCommand
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
                country = command.country,
                countryCode = command.countryCode,
                notificationPreferences = NotificationPreferenceDomain(
                    enableDailyReminder = command.enableDailyReminder,
                    preferredTime = command.preferredTime,
                    phoneNumber = command.phoneNumber,
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

package com.backend.commitoclock.presentation.model

import com.backend.commitoclock.application.service.model.UserRegistrationCommand
import com.backend.commitoclock.domain.model.NotificationMethod

class UserRegistrationRequest(
    private val username: String,
    private val githubId: String,
    private val isNotified: Boolean = false,
    private val enableDailyReminder: Boolean,
    private val preferredTime: String,
    private val notificationMethod: NotificationMethod
) {
    fun toCommand(): UserRegistrationCommand {
        return UserRegistrationCommand(
            username = username,
            githubId = githubId,
            isNotified = isNotified,
            enableDailyReminder = enableDailyReminder,
            preferredTime = preferredTime,
            notificationMethod = notificationMethod
        )
    }
}
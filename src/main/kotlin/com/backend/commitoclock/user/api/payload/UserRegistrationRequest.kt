package com.backend.commitoclock.user.api.payload

import com.backend.commitoclock.user.service.command.UserRegistrationCommand
import com.backend.commitoclock.user.domain.model.NotificationMethod

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
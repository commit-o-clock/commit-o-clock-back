package com.backend.commitoclock.user.service.command

import com.backend.commitoclock.user.domain.model.NotificationMethod

data class UserRegistrationCommand(
    val username: String,
    val githubId: String,
    val isNotified: Boolean = false,
    val enableDailyReminder: Boolean,
    val preferredTime: String,
    val notificationMethod: NotificationMethod
) {}

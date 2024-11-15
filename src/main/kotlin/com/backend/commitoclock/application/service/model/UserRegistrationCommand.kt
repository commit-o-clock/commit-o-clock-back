package com.backend.commitoclock.application.service.model

import com.backend.commitoclock.domain.model.NotificationMethod

data class UserRegistrationCommand(
    val username: String,
    val githubId: String,
    val isNotified: Boolean = false,
    val enableDailyReminder: Boolean,
    val preferredTime: String,
    val notificationMethod: NotificationMethod
) {}

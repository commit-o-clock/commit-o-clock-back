package com.backend.commitoclock.user.service.command

import com.backend.commitoclock.shared.model.NotificationMethod

data class UserRegistrationCommand(
    val username: String,
    val githubId: String,
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val countryCode: Int,
    val phoneNumber: String,
    val socialMediaId: String,
    val notificationMethod: NotificationMethod
) {}

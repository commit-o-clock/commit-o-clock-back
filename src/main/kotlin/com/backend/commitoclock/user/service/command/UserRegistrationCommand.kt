package com.backend.commitoclock.user.service.command

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.NotificationMethod

data class UserRegistrationCommand(
    val username: String,
    val githubId: String,
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val countryCode: Int,
    val country: Countries,
    val phoneNumber: String,
    val socialMediaId: String,
    val notificationMethod: NotificationMethod
) {}

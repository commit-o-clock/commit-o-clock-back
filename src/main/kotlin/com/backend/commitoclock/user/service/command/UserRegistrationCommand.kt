package com.backend.commitoclock.user.service.command

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod

data class UserRegistrationCommand(
    val username: String,
    val githubId: String,
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val countryCode: CountryCode,
    val country: Countries,
    val phoneNumber: String,
    val notificationMethod: NotificationMethod,
) {}

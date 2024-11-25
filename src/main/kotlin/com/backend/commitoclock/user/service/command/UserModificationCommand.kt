package com.backend.commitoclock.user.service.command

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod

class UserModificationCommand(
    val username: String? = null,
    val githubId: String? = null,
    val enableDailyReminder: Boolean? = null,
    val preferredTime: Int? = null,
    val countryCode: CountryCode? = null,
    val phoneNumber: String? = null,
    val country: Countries? = null,
    val notificationMethod: NotificationMethod? = null
)
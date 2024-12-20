package com.backend.commitoclock.notification.domain.model

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod

data class NotificationTarget(
    val id: String? = null,
    val username: String,
    val phoneNumber: String,
    val countryCode: CountryCode,
    val country: Countries,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod,
    val isNotified: Boolean
) {
    fun makeNotified() = copy(isNotified = true)
}


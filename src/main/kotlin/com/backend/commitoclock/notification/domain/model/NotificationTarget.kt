package com.backend.commitoclock.notification.domain.model

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.NotificationMethod

data class NotificationTarget(
    val id: String? = null,
    val username: String,
    val phoneNumber: String,
    val countryCode: Int,
    val country: Countries,
    val socialMediaId: String,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod,
    val isNotified: Boolean
) {
    fun makeNotified() = copy(isNotified = true)
}


package com.backend.commitoclock.user.domain.model

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.shared.model.NotificationMethod
import java.time.LocalDateTime

data class User(
    val id: String? = null,
    val username: String,
    val githubId: String,
    val country: Countries,
    val countryCode: Int,
    var lastCommitDate: LocalDateTime? = null,
    val commitData: MutableMap<String, Int> = mutableMapOf(),
    val notificationPreferences: NotificationPreferenceDomain
) {
    fun updateLastCommitDate(
        date: String,
        commitCount: Int
    ) {
        this.lastCommitDate = LocalDateTime.now()
        this.commitData[date] = commitCount
    }

    fun toNotificationTarget(): NotificationTarget {
        return NotificationTarget(
            username = this.username,
            phoneNumber = "",
            socialMediaId = "",
            userId = id ?: "",
            commitDate = lastCommitDate.toString(),
            notificationMethod = this.notificationPreferences.notificationMethod,
            countryCode = this.countryCode,
            country = this.country,
            isNotified = false
        )
    }
}

data class NotificationPreferenceDomain(
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val phoneNumber: String,
    val socialMediaId : String,
    val notificationMethod: NotificationMethod
)

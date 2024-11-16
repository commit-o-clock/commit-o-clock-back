package com.backend.commitoclock.user.domain.model

import com.backend.commitoclock.notification.domain.NotificationTarget
import com.backend.commitoclock.shared.model.NotificationMethod
import java.time.LocalDateTime

data class User(
    val id: String? = null,
    val username: String,
    val githubId: String,
    var lastCommitDate: LocalDateTime? = null,
    val commitData: MutableMap<String, Int> = mutableMapOf(),
    val notificationPreferences: NotificationPreferenceDomain
) {
    fun updateLastCommitDate(commitCount: Int) {
        val today = LocalDateTime.now().toLocalDate().toString()
        this.lastCommitDate = LocalDateTime.now()
        this.commitData[today] = commitCount
    }

    fun toNotificationTarget(): NotificationTarget {
        return NotificationTarget(
            username = this.username,
            phoneNumber = "",
            socialMediaId = "",
            notificationMethod = this.notificationPreferences.notificationMethod
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

package com.backend.commitoclock.domain.model

import java.time.LocalDateTime

data class User(
    val id: String? = null,
    val username: String,
    val githubId: String,
    var lastCommitDate: LocalDateTime? = null,
    var isNotified: Boolean = false,
    val notificationPreferences: NotificationPreferenceDomain
) {
    fun shouldNotify(): Boolean {
        return !isNotified && (lastCommitDate?.toLocalDate() != LocalDateTime.now().toLocalDate())
    }

    fun updateLastCommitDate(commitDate: LocalDateTime) {
        this.lastCommitDate = commitDate
        this.isNotified = false
    }

    fun markNotified() {
        this.isNotified = true
    }
}

data class NotificationPreferenceDomain(
    val enableDailyReminder: Boolean = true,
    val preferredTime: String = "09:00",
    val notificationMethod: NotificationMethod
)

enum class NotificationMethod {
    KAKAO,
    DISCORD,
    SLACK,
    EMAIL,
    SMS
}

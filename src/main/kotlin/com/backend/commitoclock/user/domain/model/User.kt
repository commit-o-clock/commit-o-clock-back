package com.backend.commitoclock.user.domain.model

import java.time.LocalDateTime

data class User(
    val id: String? = null,
    val username: String,
    val githubId: String,
    var lastCommitDate: LocalDateTime? = null,
    val commitData: MutableMap<String, Int> = mutableMapOf(),
    var isNotified: Boolean = false,
    val notificationPreferences: NotificationPreferenceDomain
) {
    fun shouldNotify(): Boolean {
        return !isNotified && (lastCommitDate?.toLocalDate() != LocalDateTime.now().toLocalDate())
    }

    fun updateLastCommitDate(commitCount: Int) {
        val today = LocalDateTime.now().toLocalDate().toString()
        this.lastCommitDate = LocalDateTime.now()
        this.commitData[today] = commitCount
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

package com.backend.commitoclock.user.domain.model

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod
import com.backend.commitoclock.user.application.dto.command.UserModificationCommand
import java.time.LocalDateTime

data class User(
    var id: String? = null,
    var username: String,
    var githubId: String,
    var country: Countries,
    var countryCode: CountryCode,
    var lastCommitDate: LocalDateTime? = null,
    var commitData: MutableMap<String, Int> = mutableMapOf(),
    var notificationPreferences: NotificationPreferenceDomain
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
            userId = id ?: "",
            commitDate = lastCommitDate.toString(),
            notificationMethod = this.notificationPreferences.notificationMethod,
            countryCode = this.countryCode,
            country = this.country,
            isNotified = false
        )
    }

    fun update(
        userModificationCommand: UserModificationCommand
    ): Map<String, Any?> {
        val updatedFields = mutableMapOf<String, Any?>()
        userModificationCommand.username?.let {
            this.username = it
            updatedFields["username"] = it
        }
        userModificationCommand.githubId?.let {
            this.githubId = it
            updatedFields["githubId"] = it
        }
        userModificationCommand.country?.let {
            this.country = it
            updatedFields["country"] = it
        }
        userModificationCommand.countryCode?.let {
            this.countryCode = it
            updatedFields["countryCode"] = it
        }
        userModificationCommand.enableDailyReminder?.let {
            this.notificationPreferences.enableDailyReminder = it
            updatedFields["notificationPreferences.enableDailyReminder"] = it
        }
        userModificationCommand.notificationMethod?.let {
            this.notificationPreferences.notificationMethod = it
            updatedFields["notificationPreferences.notificationMethod"] = it
        }
        userModificationCommand.preferredTime?.let {
            this.notificationPreferences.preferredTime = it
            updatedFields["notificationPreferences.preferredTime"] = it
        }
        userModificationCommand.phoneNumber?.let {
            this.notificationPreferences.phoneNumber = it
            updatedFields["notificationPreferences.phoneNumber"] = it
        }
        return updatedFields
    }
}

data class NotificationPreferenceDomain(
    var enableDailyReminder: Boolean,
    var preferredTime: Int,
    var phoneNumber: String,
    var notificationMethod: NotificationMethod
)

package com.backend.commitoclock.user.infra.mongo

import com.backend.commitoclock.shared.model.NotificationMethod
import com.backend.commitoclock.user.domain.model.NotificationPreferenceDomain
import com.backend.commitoclock.user.domain.model.User
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "users")
data class UserCollection(
    @Id
    val id: String? = null,
    val username: String,
    val githubId: String,
    val lastCommitDate: LocalDateTime? = null,
    val notificationPreferences: NotificationPreference
) {
    fun toDomain(): User {
        return User(
            id = id,
            username = username,
            githubId = githubId,
            lastCommitDate = lastCommitDate,
            notificationPreferences = notificationPreferences.toDomain()
        )
    }
}

data class NotificationPreference(
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val phoneNumber: String,
    val socialMediaId: String,
    val notificationMethod: NotificationMethod
) {
    fun toDomain(): NotificationPreferenceDomain {
        return NotificationPreferenceDomain(
            enableDailyReminder = enableDailyReminder,
            preferredTime = preferredTime,
            phoneNumber = phoneNumber,
            socialMediaId = socialMediaId,
            notificationMethod = notificationMethod
        )
    }
}




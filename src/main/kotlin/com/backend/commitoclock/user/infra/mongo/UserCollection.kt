package com.backend.commitoclock.user.infra.mongo

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
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
    val country: Countries,
    val countryCode: CountryCode,
    val notificationPreferences: NotificationPreference
) {
    fun toDomain(): User {
        return User(
            id = id,
            username = username,
            githubId = githubId,
            lastCommitDate = lastCommitDate,
            country = country,
            countryCode = countryCode,
            notificationPreferences = notificationPreferences.toDomain()
        )
    }

    companion object {
        fun from(user: User): UserCollection {
            return UserCollection(
                id = user.id,
                username = user.username,
                githubId = user.githubId,
                lastCommitDate = user.lastCommitDate,
                country = user.country,
                countryCode = user.countryCode,
                notificationPreferences = NotificationPreference.from(user.notificationPreferences)
            )
        }
    }
}

data class NotificationPreference(
    val enableDailyReminder: Boolean,
    val preferredTime: Int,
    val phoneNumber: String,
    val notificationMethod: NotificationMethod
) {
    fun toDomain(): NotificationPreferenceDomain {
        return NotificationPreferenceDomain(
            enableDailyReminder = enableDailyReminder,
            preferredTime = preferredTime,
            phoneNumber = phoneNumber,
            notificationMethod = notificationMethod
        )
    }

    companion object {
        fun from(domain: NotificationPreferenceDomain): NotificationPreference {
            return NotificationPreference(
                enableDailyReminder = domain.enableDailyReminder,
                preferredTime = domain.preferredTime,
                phoneNumber = domain.phoneNumber,
                notificationMethod = domain.notificationMethod
            )
        }
    }
}




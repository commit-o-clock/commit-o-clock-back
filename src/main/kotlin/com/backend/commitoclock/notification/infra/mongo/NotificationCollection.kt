package com.backend.commitoclock.notification.infra.mongo

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Document("notification")
data class NotificationCollection(
    @Id
    val id: String? = null,
    val username: String,
    val phoneNumber: String,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod,
    var isNotified: Boolean,
    val country: Countries,
    val countryCode: CountryCode,
    val updatedAt: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
    val createdAt: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
) {
    fun toDomain() = NotificationTarget(
        id = id,
        username = username,
        phoneNumber = phoneNumber,
        userId = userId,
        commitDate = commitDate,
        country = country,
        countryCode = countryCode,
        notificationMethod = notificationMethod,
        isNotified = isNotified
    )

    companion object {
        fun from(notification: NotificationTarget) = NotificationCollection(
            id = notification.id,
            username = notification.username,
            phoneNumber = notification.phoneNumber,
            userId = notification.userId,
            commitDate = notification.commitDate,
            notificationMethod = notification.notificationMethod,
            country = notification.country,
            countryCode = notification.countryCode,
            isNotified = notification.isNotified
        )
    }
}
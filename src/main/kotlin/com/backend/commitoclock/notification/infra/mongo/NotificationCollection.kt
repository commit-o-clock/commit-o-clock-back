package com.backend.commitoclock.notification.infra.mongo

import com.backend.commitoclock.notification.domain.model.NotificationTarget
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
    val socialMediaId: String,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod,
    var isNotified: Boolean,
    val updatedAt: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
    val createdAt: String = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
) {
    fun toDomain() = NotificationTarget(
        id = id,
        username = username,
        phoneNumber = phoneNumber,
        socialMediaId = socialMediaId,
        userId = userId,
        commitDate = commitDate,
        notificationMethod = notificationMethod,
        isNotified = isNotified
    )

    companion object {
        fun from(notification: NotificationTarget) = NotificationCollection(
            id = notification.id,
            username = notification.username,
            phoneNumber = notification.phoneNumber,
            socialMediaId = notification.socialMediaId,
            userId = notification.userId,
            commitDate = notification.commitDate,
            notificationMethod = notification.notificationMethod,
            isNotified = notification.isNotified
        )
    }
}
package com.backend.commitoclock.notification.domain.gateway

import com.backend.commitoclock.shared.model.NotificationMethod
import org.springframework.stereotype.Component

@Component
interface NotificationGateway {
    fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        notificationMethod: NotificationMethod
    ): Boolean
}
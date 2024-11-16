package com.backend.commitoclock.notification.infra.gateway

import org.springframework.stereotype.Component

@Component
interface NotificationGateway {
    fun sendNotification(
        phoneNumber: String,
        username: String
    )
}
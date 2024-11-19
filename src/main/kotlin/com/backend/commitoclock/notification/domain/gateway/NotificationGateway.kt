package com.backend.commitoclock.notification.domain.gateway

import com.backend.commitoclock.notification.domain.model.Countries
import org.springframework.stereotype.Component

@Component
interface NotificationGateway {
    fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        language: Countries,
    ): Boolean
}
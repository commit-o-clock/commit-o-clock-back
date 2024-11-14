package com.backend.commitoclock.domain.gateway

import org.springframework.stereotype.Component

@Component
interface NotificationGateway {
    fun sendNotification()
}
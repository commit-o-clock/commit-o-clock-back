package com.backend.commitoclock.notification.domain.gateway

import com.backend.commitoclock.shared.model.Countries
import org.springframework.stereotype.Component
import java.util.Locale.IsoCountryCode

@Component
interface NotificationGateway {
    fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        language: Countries,
        countryCode: Int
    ): Boolean
}
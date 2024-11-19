package com.backend.commitoclock.notification.infra.gateway

import com.backend.commitoclock.notification.domain.gateway.NotificationGateway
import com.backend.commitoclock.shared.model.NotificationMethod
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component("smsGateway")
class SmsGateway(
    @Value("sms.url") private val url: String,
    @Value("sms.key") private val key: String,
    @Value("sms.secret") private val secret: String,
    private val restClient: RestClient
) : NotificationGateway {
    override fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        notificationMethod: NotificationMethod
    ): Boolean {
        val response: ResponseEntity<SmsResponse> = restClient.post()
            .uri(url)
            .header("Authorization", "Bearer $key:$secret")
            .body(
                // TODO : fill the correct body
                mapOf(
                    "to" to phoneNumber,
                    "from" to "01012345678",
                    "text" to "Hello, $username"
                )
            )
            .retrieve()
            .toEntity(SmsResponse::class.java)

        return response.statusCode.is2xxSuccessful
    }

    data class SmsResponse(
        val to: String,
        val from: String,
        val type: String,
        val country: String,
        val messageId: String,
        val statusCode: String,
        val statusMessage: String,
        val accountId: String
    ) {}
}

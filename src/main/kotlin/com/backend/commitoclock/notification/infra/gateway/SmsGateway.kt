package com.backend.commitoclock.notification.infra.gateway

import com.backend.commitoclock.notification.domain.gateway.NotificationGateway
import com.backend.commitoclock.notification.domain.model.Countries
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.random.Random

private val logger = KotlinLogging.logger {}

@Component("smsGateway")
class SmsGateway(
    @Value("\${sms.url}") private val url: String,
    @Value("\${sms.key}") private val key: String,
    @Value("\${sms.secret}") private val secret: String,
    @Value("\${sms.sender}") private val sender: String,
    private val restClient: RestClient
) : NotificationGateway {

    override fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        language: Countries,
    ): Boolean {
        val response: ResponseEntity<SmsResponse> = restClient.post()
            .uri(url)
            .header(generateAuthorizationHeader(key, secret))
            .body(
                mapOf(
                    "from" to sender,
                    "to" to phoneNumber,
                    "text" to message,
                    "country" to language.countryCode()
                )
            )
            .retrieve()
            .toEntity(SmsResponse::class.java)

        logger.info { "SMS sent to $phoneNumber" }
        return response.statusCode.is2xxSuccessful
    }

    fun generateAuthorizationHeader(
        apiKey: String,
        apiSecret: String,
        algorithm: String = "HmacSHA256"
    ): String {
        val dateTime = ZonedDateTime.now().format(DateTimeFormatter.ISO_INSTANT)
        val salt = generateRandomSalt(12, 64)
        val data = dateTime + salt
        val signature = createHmacSignature(data, apiSecret, algorithm)
        return "Authorization: $algorithm apiKey=$apiKey, date=$dateTime, salt=$salt, signature=$signature"
    }

    fun generateRandomSalt(minLength: Int, maxLength: Int): String {
        val length = Random.nextInt(minLength, maxLength + 1)
        val saltBytes = ByteArray(length)
        Random.nextBytes(saltBytes)
        return Base64.getEncoder().encodeToString(saltBytes)
    }

    fun createHmacSignature(data: String, secret: String, algorithm: String): String {
        val mac = Mac.getInstance(algorithm)
        val secretKeySpec = SecretKeySpec(secret.toByteArray(), algorithm)
        mac.init(secretKeySpec)
        val hmacBytes = mac.doFinal(data.toByteArray())
        return Base64.getEncoder().encodeToString(hmacBytes)
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

package com.backend.commitoclock.notification.infra.gateway

import com.backend.commitoclock.notification.domain.gateway.NotificationGateway
import com.backend.commitoclock.shared.model.Countries
import io.github.cdimascio.dotenv.Dotenv
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

private val logger = KotlinLogging.logger {}
private val jsonParser = Json { ignoreUnknownKeys = true }

@Component("smsGateway")
class SmsGateway(
    private val restClient: RestClient,
    private val dotenv: Dotenv
) : NotificationGateway {
    private val url = dotenv["SMS_URL"]
    private val key = dotenv["SMS_KEY"]
    private val secret = dotenv["SMS_SECRET"]
    private val sender = dotenv["SMS_SENDER"]

    override fun sendNotification(
        phoneNumber: String,
        username: String,
        message: String,
        language: Countries
    ): Boolean {

        val jsonBody = jsonParser.encodeToString(
            SmsRequest(
                message = Message(
                    from = sender,
                    to = phoneNumber,
                    text = message,
                    country = language.countryCode
                )
            )
        )

        logger.debug { "Request JSON: $jsonBody" }

        val response = restClient.post()
            .uri(url)
            .header("Authorization", generateHeader(key, secret))
            .header("Content-Type", "application/json")
            .body(jsonBody)
            .retrieve()
            .toEntity(String::class.java)

        val smsResponse: SmsResponse = jsonParser.decodeFromString(response.body ?: "{}")
        logger.info { "SMS sent to $phoneNumber with status ${response.statusCode}" }
        return response.statusCode.is2xxSuccessful
    }

    private fun generateHeader(apiKey: String, apiSecret: String): String {
        val dateTime = ZonedDateTime.now(ZoneOffset.UTC)
            .truncatedTo(java.time.temporal.ChronoUnit.SECONDS)
            .format(DateTimeFormatter.ISO_INSTANT)
        val salt = generateRandomSalt(16)
        val data = "$dateTime$salt"
        val signature = createHmacSignature(data, apiSecret)

        return "HMAC-SHA256 apiKey=$apiKey, date=$dateTime, salt=$salt, signature=$signature"
    }

    private fun createHmacSignature(data: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        val secretKeySpec = SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256")
        mac.init(secretKeySpec)
        val hmacBytes = mac.doFinal(data.toByteArray(Charsets.UTF_8))
        return hmacBytes.joinToString("") { "%02x".format(it) }
    }

    private fun generateRandomSalt(length: Int): String {
        val charset = "1234567890abcdefghijklmnopqrstuvwxyz"
        return (1..length).map { charset.random() }.joinToString("")
    }

    @Serializable
    data class SmsRequest(val message: Message)

    @Serializable
    data class Message(
        val from: String,
        val to: String,
        val text: String,
        val country: String
    )

    @Serializable
    data class SmsResponse(
        val to: String,
        val from: String,
        val type: String,
        val country: String,
        val messageId: String,
        val statusCode: String,
        val statusMessage: String,
        val accountId: String,
        val groupId: String? = null
    )
}

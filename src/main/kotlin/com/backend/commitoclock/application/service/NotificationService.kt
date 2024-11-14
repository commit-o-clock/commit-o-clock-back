package com.backend.commitoclock.application.service

import com.backend.commitoclock.domain.gateway.NotificationGateway
import com.backend.commitoclock.domain.model.NotificationMethod
import com.backend.commitoclock.domain.model.User
import com.backend.commitoclock.domain.repository.UserRepository
import com.backend.commitoclock.infrastructure.concurrent.NotificationQueue
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class NotificationService(
    @Qualifier("inMemoryQueue") private val notificationQueue: NotificationQueue,
    @Qualifier("kakaoGateway") private val kakaoGateway: NotificationGateway,
    private val userRepository: UserRepository
) {
    fun stackNotification(user: User) {
        notificationQueue.add(user)
    }
    fun sendNotification() {
        while (!notificationQueue.isEmpty()) {
            val user = notificationQueue.poll()
            if (user?.shouldNotify() == false) {
                continue
            }
            val notificationPreference = user?.notificationPreferences
            when (notificationPreference?.notificationMethod) {
                NotificationMethod.EMAIL -> {
                    // TODO
                }

                NotificationMethod.SMS -> {
                    // TODO
                }

                NotificationMethod.KAKAO -> {
                    kakaoGateway.sendNotification()
                }

                NotificationMethod.DISCORD -> {
                    // TODO
                }

                NotificationMethod.SLACK -> {
                    // TODO
                }
                null -> {
                    logger.info { "Notification method not set for user: ${user?.username}" }
                }
            }
            user?.markNotified()
            user?.let { userRepository.save(it) }
        }
    }
}
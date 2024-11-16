package com.backend.commitoclock.notification.service

import com.backend.commitoclock.notification.domain.NotificationTarget
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import com.backend.commitoclock.notification.infra.gateway.NotificationGateway
import com.backend.commitoclock.shared.model.NotificationMethod
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class NotificationService(
    @Qualifier("inMemoryQueue") private val notificationTargetQueue: NotificationTargetQueue,
    @Qualifier("kakaoGateway") private val kakaoGateway: NotificationGateway,
    private val userRepository: UserRepository
) {
    fun stackNotification(target: NotificationTarget) {
        notificationTargetQueue.add(target)
    }

    fun sendNotification() {
        while (!notificationTargetQueue.isEmpty()) {
            val target = notificationTargetQueue.poll()
            when (target?.notificationMethod) {
                NotificationMethod.EMAIL -> {
                    // TODO
                }

                NotificationMethod.WHATS_APP -> {
                    // TODO
                }

                NotificationMethod.KAKAO -> {
                    kakaoGateway.sendNotification()
                }

                null -> {
                    logger.info { "Notification method not set for user: ${target?.username}" }
                }
            }
        }
    }
}
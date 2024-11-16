package com.backend.commitoclock.notification.service

import com.backend.commitoclock.user.domain.model.NotificationMethod
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.commit.infra.concurrent.UncommitUserQueue
import com.backend.commitoclock.notification.infra.gateway.NotificationGateway
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}
@Service
class NotificationService(
    @Qualifier("inMemoryQueue") private val uncommitUserQueue: UncommitUserQueue,
    @Qualifier("kakaoGateway") private val kakaoGateway: NotificationGateway,
    private val userRepository: UserRepository
) {
    fun stackNotification(user: User) {
        uncommitUserQueue.add(user)
    }

    fun sendNotification() {
        while (!uncommitUserQueue.isEmpty()) {
            val user = uncommitUserQueue.poll()
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
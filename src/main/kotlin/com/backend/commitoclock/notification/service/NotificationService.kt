package com.backend.commitoclock.notification.service

import com.backend.commitoclock.commit.service.CommitNotificationProcessor
import com.backend.commitoclock.notification.domain.NotificationTarget
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
    private val commitNotificationProcessor: CommitNotificationProcessor
) {
    fun stackNotification(target: NotificationTarget) {
        notificationTargetQueue.add(target)
    }
    fun sendNotification(date: String) {
        val validTargets =
            commitNotificationProcessor.processNotifications(date, notificationTargetQueue)

        validTargets.forEach { target ->
            when (target.notificationMethod) {
                NotificationMethod.EMAIL -> {
                    // TODO
                    log(target)
                }

                NotificationMethod.WHATS_APP -> {
                    // TODO
                    log(target)
                }

                NotificationMethod.KAKAO -> {
                    kakaoGateway.sendNotification(
                        target.phoneNumber,
                        target.username
                    )
                    log(target)
                }
            }
        }
    }

    private fun log(target: NotificationTarget) {
        logger.info {
            """
                                ${target.notificationMethod} notification 
                                sent to ${target.phoneNumber} 
                                (name : ${target.username} )"
                            """.trimIndent()
        }
    }
}
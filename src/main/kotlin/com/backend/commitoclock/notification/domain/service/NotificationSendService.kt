package com.backend.commitoclock.notification.domain.service

import com.backend.commitoclock.commit.application.service.CommitNotificationProcessor
import com.backend.commitoclock.notification.domain.gateway.NotificationGateway
import com.backend.commitoclock.notification.domain.model.Messages
import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class NotificationSendService(
    @Qualifier("inMemoryQueue") private val notificationTargetQueue: NotificationTargetQueue,
    private val notificationGateway: NotificationGateway,
    private val commitNotificationProcessor: CommitNotificationProcessor
) {
    fun stackNotification(target: NotificationTarget) {
        notificationTargetQueue.add(target)
    }

    fun sendNotification(date: String) {
        val validTargets = commitNotificationProcessor.processNotifications(
            date,
            notificationTargetQueue
        )
        validTargets.forEach { target ->
            val message = Messages.getMessage(target.username, target.country)
            val result = notificationGateway.sendNotification(
                target.phoneNumber,
                target.username,
                message,
                target.countryCode
            )
            if (result) log(target)
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
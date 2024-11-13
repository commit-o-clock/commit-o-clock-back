package com.backend.commitoclock.application.service

import com.backend.commitoclock.domain.gateway.NotificationGateway
import com.backend.commitoclock.domain.model.NotificationMethod
import com.backend.commitoclock.domain.model.User
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentLinkedQueue

@Service
class NotificationService(
    private val notificationGateway: NotificationGateway
) {
    private val notificationQueue = ConcurrentLinkedQueue<User>()

    fun stackNotification(user: User) {
        notificationQueue.add(user)
    }

    fun sendNotification() {
        while (notificationQueue.isNotEmpty()) {
            val user = notificationQueue.poll()
            val notificationPreference = user.notificationPreferences
            when (notificationPreference.notificationMethod) {
                NotificationMethod.EMAIL -> {
                    // TODO
                }

                NotificationMethod.SMS -> {
                    // TODO
                }

                NotificationMethod.KAKAO -> {
                    // TODO
                }

                NotificationMethod.DISCORD -> {
                    // TODO
                }

                NotificationMethod.SLACK -> {
                    // TODO
                }
            }
        }
    }
}
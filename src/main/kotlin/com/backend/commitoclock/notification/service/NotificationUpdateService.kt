package com.backend.commitoclock.notification.service

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationUpdateService(
    val notificationRepository: NotificationRepository
) {
    fun saveAll(notificationTargets: List<NotificationTarget>) {
        notificationRepository.saveAll(notificationTargets)
    }

    fun updateAll(notificationTargets: List<NotificationTarget>) {
        notificationTargets.forEach {
            it.makeNotified()
        }
        notificationRepository.updateAll(notificationTargets)
    }
}
package com.backend.commitoclock.notification.application.service.write

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationWriteService(
    val notificationRepository: NotificationRepository
) {
    @Transactional
    fun saveAll(notificationTargets: List<NotificationTarget>) {
        notificationRepository.saveAll(notificationTargets)
    }

    @Transactional
    fun updateAll(notificationTargets: List<NotificationTarget>) {
        notificationTargets.forEach {
            it.makeNotified()
        }
        notificationRepository.updateAll(notificationTargets)
    }
}
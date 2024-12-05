package com.backend.commitoclock.notification.application.service.read

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationReadService(
    private val notificationRepository: NotificationRepository
) {
    @Transactional(readOnly = true)
    fun getCommitsByDate(date: String): List<NotificationTarget> {
        return notificationRepository.findByCreatedAt(date)
    }

}

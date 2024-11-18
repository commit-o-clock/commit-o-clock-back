package com.backend.commitoclock.notification.service

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.repository.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationInquiryService(
    private val notificationRepository: NotificationRepository
) {
    fun getCommitsByDate(date: String): List<NotificationTarget> {
        return notificationRepository.findByCreatedAt(date)

    }


}

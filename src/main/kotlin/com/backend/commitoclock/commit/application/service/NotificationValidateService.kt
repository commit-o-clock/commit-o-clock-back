package com.backend.commitoclock.commit.application.service

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import org.springframework.stereotype.Service

@Service
class NotificationValidateService {
    fun validate(
        notificationTarget: List<NotificationTarget>,
        userId: String
    ): Boolean {
        return notificationTarget.any { it.userId == userId }
    }
}

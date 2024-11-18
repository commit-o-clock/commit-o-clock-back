package com.backend.commitoclock.commit.service

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import com.backend.commitoclock.notification.service.NotificationInquiryService
import com.backend.commitoclock.notification.service.NotificationUpdateService
import org.springframework.stereotype.Component

@Component
class CommitNotificationProcessor(
    private val notificationValidateService: NotificationValidateService,
    private val notificationInquiryService: NotificationInquiryService,
    private val notificationUpdateService: NotificationUpdateService
) {
    fun processNotifications(
        date: String,
        notificationQueue: NotificationTargetQueue
    ): List<NotificationTarget> {
        val targetIds = notificationInquiryService.getCommitsByDate(date)
        val validTargets = mutableListOf<NotificationTarget>()

        while (!notificationQueue.isEmpty()) {
            val target = notificationQueue.poll()
            if (target?.userId.isNullOrBlank() ||
                !notificationValidateService.validate(targetIds, target!!.userId)
            ) {
                continue
            }
            validTargets.add(target)
        }
        notificationUpdateService.updateAll(validTargets)
        return validTargets
    }
}
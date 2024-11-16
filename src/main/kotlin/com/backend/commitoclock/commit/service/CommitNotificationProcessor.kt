package com.backend.commitoclock.commit.service

import com.backend.commitoclock.notification.domain.NotificationTarget
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import org.springframework.stereotype.Component

@Component
class CommitNotificationProcessor(
    private val commitInquiryService: CommitInquiryService,
    private val commitValidateService: CommitValidateService,
    private val commitUpdateService: CommitUpdateService
) {
    fun processNotifications(
        date: String,
        notificationQueue: NotificationTargetQueue
    ): List<NotificationTarget> {
        val targetIds = commitInquiryService.getCommitsOfToday(date)
        val validTargets = mutableListOf<NotificationTarget>()

        while (!notificationQueue.isEmpty()) {
            val target = notificationQueue.poll()
            if (target?.userId.isNullOrBlank() ||
                !commitValidateService.validate(targetIds, target!!.userId)) {
                continue
            }
            validTargets.add(target)
        }
        commitUpdateService.updateAll(notificationQueue, targetIds)
        return validTargets
    }
}
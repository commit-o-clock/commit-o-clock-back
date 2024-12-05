package com.backend.commitoclock.commit.application.service

import com.backend.commitoclock.notification.application.service.read.NotificationReadService
import com.backend.commitoclock.notification.application.service.write.NotificationWriteService
import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import org.springframework.stereotype.Component

@Component
class CommitNotificationProcessor(
    private val notificationValidateService: NotificationValidateService,
    private val notificationReadService: NotificationReadService,
    private val notificationWriteService: NotificationWriteService
) {
    fun processNotifications(
        date: String,
        notificationQueue: NotificationTargetQueue
    ): List<NotificationTarget> {
        val targetIds = notificationReadService.getCommitsByDate(date)
        val validTargets = mutableListOf<NotificationTarget>()

        while (!notificationQueue.isEmpty()) {
            val target = notificationQueue.poll()
            if (target?.userId.isNullOrBlank() ||
                !notificationValidateService.validate(targetIds, target!!.userId)
            ) { continue }
            validTargets.add(target)
        }
        notificationWriteService.updateAll(validTargets)
        return validTargets
    }
}
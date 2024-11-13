package com.backend.commitoclock.application.schedular

import com.backend.commitoclock.application.service.CommitCheckService
import com.backend.commitoclock.application.service.NotificationService
import com.backend.commitoclock.application.service.UserInquiryService
import com.backend.commitoclock.domain.model.User
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component
class CommitCheckScheduler(
    private val commitCheckService: CommitCheckService,
    private val userInquiryService: UserInquiryService,
    private val notificationService: NotificationService
) {
    private val notificationQueue = ConcurrentLinkedQueue<User>()

    @Scheduled(cron = "0 0 21 * * ?")
    fun stackNotification() {
        userInquiryService.findAllUsers().forEach { user ->
            if (!commitCheckService.hasUserCommittedToday(user.githubId)) {
                notificationService.stackNotification(user)
            }
        }
        notificationService.sendNotification()
    }
}
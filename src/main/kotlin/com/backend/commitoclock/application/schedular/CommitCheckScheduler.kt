package com.backend.commitoclock.application.schedular

import com.backend.commitoclock.application.service.CommitCheckService
import com.backend.commitoclock.application.service.NotificationService
import com.backend.commitoclock.application.service.UserInquiryService
import com.backend.commitoclock.application.service.UserUpdateService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CommitCheckScheduler(
    private val commitCheckService: CommitCheckService,
    private val userInquiryService: UserInquiryService,
    private val userUpdateService: UserUpdateService,
    private val notificationService: NotificationService
) {
    @Scheduled(cron = "0 0 21 * * ?")
    fun stackNotification() {
        userInquiryService.findAllUsers().forEach { user ->
            val commitsOfToday = commitCheckService.hasCommittedToday(user.githubId)
            if (commitsOfToday > 0) {
                notificationService.stackNotification(user)
                user.updateLastCommitDate(commitsOfToday)
                userUpdateService.save(user)
            } else {
                notificationService.stackNotification(user)
            }
        }
        notificationService.sendNotification()
    }
}
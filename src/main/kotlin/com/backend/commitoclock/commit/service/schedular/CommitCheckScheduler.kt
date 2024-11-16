package com.backend.commitoclock.commit.service.schedular

import com.backend.commitoclock.commit.service.CommitCheckService
import com.backend.commitoclock.notification.service.NotificationService
import com.backend.commitoclock.user.service.UserInquiryService
import com.backend.commitoclock.user.service.UserUpdateService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalTime

@Component
class CommitCheckScheduler(
    private val commitCheckService: CommitCheckService,
    private val userInquiryService: UserInquiryService,
    private val userUpdateService: UserUpdateService,
    private val notificationService: NotificationService
) {
    @Scheduled(cron = "0 0 * * * ?")
    fun stackNotification() {
        userInquiryService.getTargetUsers(LocalTime.now().hour)
            .forEach { user ->
                val commitsOfToday = commitCheckService.hasCommittedToday(user.githubId)
                if (commitsOfToday > 0) {
                    user.updateLastCommitDate(commitsOfToday)
                    userUpdateService.save(user)
                } else {
                    notificationService.stackNotification(user.toNotificationTarget())
            }
        }
        notificationService.sendNotification()
    }
}
package com.backend.commitoclock.commit.service.schedular

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.service.CommitCheckService
import com.backend.commitoclock.commit.service.CommitUpdateService
import com.backend.commitoclock.notification.service.NotificationService
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.service.UserInquiryService
import com.backend.commitoclock.user.service.UserUpdateService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class CommitCheckScheduler(
    private val commitCheckService: CommitCheckService,
    private val commitUpdateService: CommitUpdateService,
    private val userInquiryService: UserInquiryService,
    private val userUpdateService: UserUpdateService,
    private val notificationService: NotificationService
) {
    @Scheduled(cron = "0 0 * * * ?")
    fun stackNotification() {
        val commits = mutableListOf<Commit>()
        val users = mutableListOf<User>()
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        userInquiryService.getTargetUsers(LocalTime.now().hour)
            .forEach { user ->
                val commitsOfToday = commitCheckService.hasCommitted(user.githubId, today)
                if (commitsOfToday > 0) {
                    user.updateLastCommitDate(today, commitsOfToday)
                    users.add(user)
                    val commit = createCommit(user, today, commitsOfToday)
                    commits.add(commit)
                } else {
                    notificationService.stackNotification(user.toNotificationTarget())
            }
                userUpdateService.saveAll(users)
                commitUpdateService.saveAll(commits)
        }
        notificationService.sendNotification(today)
    }

    private fun createCommit(
        user: User,
        date: String,
        commitsOfToday: Int
    ) =
        Commit(
            null,
            user.id!!,
            user.githubId,
            date,
            commitsOfToday,
            false
        )
}
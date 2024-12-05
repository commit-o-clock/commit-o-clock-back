package com.backend.commitoclock.commit.application.schedular

import com.backend.commitoclock.commit.application.service.CommitCheckService
import com.backend.commitoclock.commit.application.service.write.CommitWriteService
import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.notification.application.service.write.NotificationWriteService
import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.service.NotificationSendService
import com.backend.commitoclock.user.application.service.read.UserReadService
import com.backend.commitoclock.user.application.service.write.UserWriteService
import com.backend.commitoclock.user.domain.model.User
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Component
class CommitCheckScheduler(
    private val commitCheckService: CommitCheckService,
    private val userReadService: UserReadService,
    private val userWriteService: UserWriteService,
    private val commitWriteService: CommitWriteService,
    private val notificationSendService: NotificationSendService,
    private val notificationWriteService: NotificationWriteService
) {
    @Scheduled(cron = "0 0 * * * ?")
    fun stackNotification() {
        val today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val users = userReadService.getTargetUsers(LocalTime.now().hour)

        if (users.isEmpty()) {
            println("No users to process for hour ${LocalTime.now().hour}")
            return
        }

        val (usersToSave, commits, notifications) = processUsers(users, today)
        saveInBatches(usersToSave, userWriteService::saveAll)
        saveInBatches(commits, commitWriteService::saveAll)
        saveInBatches(notifications, notificationWriteService::saveAll)

        notificationSendService.sendNotification(today)
    }

    private fun processUsers(
        users: List<User>,
        today: String
    ): Triple<List<User>, List<Commit>, List<NotificationTarget>> {
        val usersToSave = mutableListOf<User>()
        val commits = mutableListOf<Commit>()
        val notifications = mutableListOf<NotificationTarget>()

        users.forEach { user ->
            val commitsOfToday = commitCheckService.hasCommitted(user.githubId, today)
            if (commitsOfToday > 0) {
                user.updateLastCommitDate(today, commitsOfToday)
                usersToSave.add(user)
                commits.add(createCommit(user, today, commitsOfToday))
                println("User ${user.githubId} updated with $commitsOfToday commits.")
            } else {
                val target = user.toNotificationTarget()
                notifications.add(target)
                notificationSendService.stackNotification(target)
                println("Notification stacked for user ${user.githubId}.")
            }
        }

        return Triple(usersToSave, commits, notifications)
    }

    private fun <T> saveInBatches(
        data: List<T>,
        saveFunction: (List<T>) -> Unit,
        batchSize: Int = 1000
    ) {
        data.chunked(batchSize).forEach { batch ->
            saveFunction(batch)
        }
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
            commitsOfToday
        )
}

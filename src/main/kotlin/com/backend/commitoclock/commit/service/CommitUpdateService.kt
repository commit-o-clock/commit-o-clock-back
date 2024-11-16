package com.backend.commitoclock.commit.service

import com.backend.commitoclock.commit.domain.model.Commit
import com.backend.commitoclock.commit.domain.repository.CommitRepository
import com.backend.commitoclock.notification.infra.concurrent.NotificationTargetQueue
import org.springframework.stereotype.Service

@Service
class CommitUpdateService(
    private val commitRepository: CommitRepository
) {
    fun saveAll(commits: List<Commit>) {
        // TODO
        if (commits.isEmpty()) {
            return
        }
    }

    fun updateAll(
        notificationTargetQueue: NotificationTargetQueue,
        targetCommits: List<Commit>
    ) {
        while(!notificationTargetQueue.isEmpty()) {
            val target = notificationTargetQueue.poll()
            targetCommits.forEach() {
                if(target!!.userId === it.userId){
                    it.makeNotified()
                }
            }
        }
        commitRepository.updateAll(targetCommits)

    }

}

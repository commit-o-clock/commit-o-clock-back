package com.backend.commitoclock.infrastructure.concurrent

import com.backend.commitoclock.domain.model.User
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component("inMemoryQueue")
class InMemoryNotificationQueue : NotificationQueue {
    private val queue = ConcurrentLinkedQueue<User>()

    override fun add(user: User) {
        queue.add(user)
    }

    override fun poll(): User? {
        return queue.poll()
    }

    override fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
}
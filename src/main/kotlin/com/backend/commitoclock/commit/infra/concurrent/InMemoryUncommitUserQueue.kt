package com.backend.commitoclock.commit.infra.concurrent

import com.backend.commitoclock.user.domain.model.User
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component("inMemoryQueue")
class InMemoryUncommitUserQueue : UncommitUserQueue {
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
package com.backend.commitoclock.notification.infra.concurrent

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentLinkedQueue

@Component("inMemoryQueue") // TODO : Change to Kafka
class InMemoryNotificationTargetQueue : NotificationTargetQueue {
    private val queue = ConcurrentLinkedQueue<NotificationTarget>()

    override fun add(target: NotificationTarget) {
        queue.add(target)
    }

    override fun poll(): NotificationTarget? {
        return queue.poll()
    }

    override fun isEmpty(): Boolean {
        return queue.isEmpty()
    }
}
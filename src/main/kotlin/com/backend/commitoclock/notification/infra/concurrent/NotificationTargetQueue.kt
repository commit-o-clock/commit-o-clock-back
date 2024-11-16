package com.backend.commitoclock.notification.infra.concurrent

import com.backend.commitoclock.notification.domain.NotificationTarget
import com.backend.commitoclock.user.domain.model.User

interface NotificationTargetQueue {
    fun add(user: NotificationTarget)
    fun poll(): NotificationTarget?
    fun isEmpty(): Boolean
}
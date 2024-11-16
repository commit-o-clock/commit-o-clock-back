package com.backend.commitoclock.notification.infra.concurrent

import com.backend.commitoclock.notification.domain.NotificationTarget

interface NotificationTargetQueue {
    fun add(target: NotificationTarget)
    fun poll(): NotificationTarget?
    fun isEmpty(): Boolean
}
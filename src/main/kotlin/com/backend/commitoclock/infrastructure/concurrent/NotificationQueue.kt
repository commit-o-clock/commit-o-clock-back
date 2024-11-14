package com.backend.commitoclock.infrastructure.concurrent

import com.backend.commitoclock.domain.model.User

interface NotificationQueue {
    fun add(user: User)
    fun poll(): User?
    fun isEmpty(): Boolean
}
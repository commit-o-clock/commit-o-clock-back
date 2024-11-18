package com.backend.commitoclock.notification.domain.repository

import com.backend.commitoclock.notification.domain.model.NotificationTarget

interface NotificationRepository {
    fun findByCreatedAt(createdAt: String): List<NotificationTarget>
    fun saveAll(notifications: List<NotificationTarget>)
    fun updateAll(notificationTargets: List<NotificationTarget>)
}
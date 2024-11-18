package com.backend.commitoclock.notification.domain.model

import com.backend.commitoclock.shared.model.NotificationMethod

class NotificationTarget(
    val id: String? = null,
    val username: String,
    val phoneNumber: String,
    val socialMediaId: String,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod,
    var isNotified: Boolean
) {
    fun makeNotified() {
        this.isNotified = true
    }
}


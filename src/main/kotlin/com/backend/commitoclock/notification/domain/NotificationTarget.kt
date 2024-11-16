package com.backend.commitoclock.notification.domain

import com.backend.commitoclock.shared.model.NotificationMethod

class NotificationTarget(
    val username: String,
    val phoneNumber: String,
    val socialMediaId: String,
    val userId: String,
    val commitDate: String,
    val notificationMethod: NotificationMethod
) {
}


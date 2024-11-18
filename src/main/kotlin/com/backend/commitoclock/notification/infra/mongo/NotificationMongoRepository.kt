package com.backend.commitoclock.notification.infra.mongo

import org.springframework.data.mongodb.repository.MongoRepository

interface NotificationMongoRepository : MongoRepository<NotificationCollection, String> {
    fun findByCreatedAt(createdAt: String): List<NotificationCollection>
}
package com.backend.commitoclock.notification.infra.repository

import com.backend.commitoclock.notification.domain.model.NotificationTarget
import com.backend.commitoclock.notification.domain.repository.NotificationRepository
import com.backend.commitoclock.notification.infra.mongo.NotificationCollection
import com.backend.commitoclock.notification.infra.mongo.NotificationMongoRepository
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Repository
class NotificationRepositoryImpl(
    private val notificationMongoRepository: NotificationMongoRepository,
    private val mongoTemplate: MongoTemplate
) : NotificationRepository {

    @Transactional
    override fun saveAll(notifications: List<NotificationTarget>) {
        val bulkOps = mongoTemplate.bulkOps(
            BulkOperations.BulkMode.UNORDERED,
            NotificationCollection::class.java
        )
        notifications.forEach { notification ->
            bulkOps.insert(
                NotificationCollection.from(
                    notification
                )
            )
        }
        bulkOps.execute()
    }

    @Transactional
    override fun updateAll(notificationTargets: List<NotificationTarget>) {
        val bulkOps = mongoTemplate.bulkOps(
            org.springframework.data.mongodb.core.BulkOperations.BulkMode.UNORDERED,
            NotificationCollection::class.java
        )
        notificationTargets.forEach { notificationTarget ->
            val query = Query(Criteria.where("id").`is`(notificationTarget.id))
            val update = Update()
                .set("isNotified", notificationTarget.isNotified)
                .set("updatedAt", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE))

            bulkOps.upsert(query, update)
        }
        bulkOps.execute()
    }

    @Transactional(readOnly = true)
    override fun findByCreatedAt(createdAt: String): List<NotificationTarget> {
        return notificationMongoRepository.findByCreatedAt(createdAt).map { it.toDomain() }
    }
}
package com.backend.commitoclock.user.service.result

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.user.api.payload.UserInformationResponse
import com.backend.commitoclock.user.domain.model.NotificationPreferenceDomain
import java.time.LocalDateTime

class UserInformationResult(
    val id: String? = null,
    val username: String,
    val githubId: String,
    val country: Countries,
    val countryCode: CountryCode,
    val lastCommitDate: LocalDateTime? = null,
    val commitData: MutableMap<String, Int> = mutableMapOf(),
    val notificationPreferences: NotificationPreferenceDomain
) {
    fun toResponse(): UserInformationResponse {
        return UserInformationResponse(
            id = this.id,
            username = this.username,
            githubId = this.githubId,
            country = this.country,
            countryCode = this.countryCode,
            lastCommitDate = this.lastCommitDate,
            commitData = this.commitData,
            notificationPreferences = this.notificationPreferences
        )
    }
}

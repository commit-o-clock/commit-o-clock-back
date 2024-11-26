package com.backend.commitoclock.user.api.payload

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.user.domain.model.NotificationPreferenceDomain
import java.time.LocalDateTime

class UserInformationResponse(
    val id: String? = null,
    val username: String,
    val githubId: String,
    val country: Countries,
    val countryCode: CountryCode,
    val lastCommitDate: LocalDateTime? = null,
    val commitData: MutableMap<String, Int> = mutableMapOf(),
    val notificationPreferences: NotificationPreferenceDomain
) {
}

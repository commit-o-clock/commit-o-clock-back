package com.backend.commitoclock.user.presentation.payload

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod
import com.backend.commitoclock.user.application.dto.command.UserModificationCommand
import com.mongodb.lang.Nullable
import jakarta.validation.constraints.Size

class UserModificationRequest(
    @field:Nullable private val username: String,
    @field:Nullable private val githubId: String,
    @field:Nullable private val enableDailyReminder: Boolean,
    @field:Nullable @field:Size(min = 1, max = 23) private val preferredTime: Int,
    @field:Nullable private val countryCode: CountryCode,
    @field:Nullable private val phoneNumber: String,
    @field:Nullable private val country: Countries,
    @field:Nullable private val notificationMethod: NotificationMethod
) {
    fun toCommand(): UserModificationCommand {
        return UserModificationCommand(
            username = username,
            githubId = githubId,
            enableDailyReminder = enableDailyReminder,
            preferredTime = preferredTime,
            countryCode = countryCode,
            phoneNumber = phoneNumber,
            country = country,
            notificationMethod = notificationMethod
        )
    }
}
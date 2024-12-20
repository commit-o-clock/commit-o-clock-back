package com.backend.commitoclock.user.presentation.payload

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.shared.model.CountryCode
import com.backend.commitoclock.shared.model.NotificationMethod
import com.backend.commitoclock.user.application.dto.command.UserRegistrationCommand
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class UserRegistrationRequest(
    @field:NotNull private val username: String,
    @field:NotNull private val githubId: String,
    @field:NotNull private val enableDailyReminder: Boolean,
    @field:Size(min = 1, max = 23) private val preferredTime: Int,
    @field:NotNull private val countryCode: CountryCode,
    @field:NotNull private val phoneNumber: String,
    @field:NotNull private val country: Countries,
    @field:NotNull private val notificationMethod: NotificationMethod
) {
    fun toCommand(): UserRegistrationCommand {
        return UserRegistrationCommand(
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
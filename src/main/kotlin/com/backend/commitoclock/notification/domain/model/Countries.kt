package com.backend.commitoclock.notification.domain.model

enum class Countries(private val countryCode: String) {
    KOREA("82"),
    USA("1");

    fun countryCode(): String {
        return countryCode
    }

}
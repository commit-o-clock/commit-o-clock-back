package com.backend.commitoclock.notification.infra

import com.backend.commitoclock.shared.model.Countries
import com.backend.commitoclock.notification.domain.model.Messages
import com.backend.commitoclock.notification.infra.gateway.SmsGateway
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/test")
class TestController(
    private val smsGateway: SmsGateway
) {
    @GetMapping
    fun test() {
        smsGateway.sendNotification("010-8498-3484", "test", Messages.getMessage("현수", Countries
            .USA),
            Countries
            .KOREA)
    }
}
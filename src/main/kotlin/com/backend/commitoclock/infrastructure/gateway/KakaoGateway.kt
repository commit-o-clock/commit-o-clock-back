package com.backend.commitoclock.infrastructure.gateway

import com.backend.commitoclock.domain.gateway.NotificationGateway
import org.springframework.stereotype.Component

@Component("kakaoGateway")
class KakaoGateway : NotificationGateway {
    override fun sendNotification() {
        // TODO
    }
}
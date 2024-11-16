package com.backend.commitoclock.infrastructure.gateway

import com.backend.commitoclock.commit.infra.gateway.CommitGateway
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("CommitGateway 테스트")
class CommitGatewayTest @Autowired constructor(
    private val commitGateway: CommitGateway
) {
    @Test
    @DisplayName("유효한 githubId 에 대해 커밋 개수 반환해야 함")
    fun `fetchCommitData should retrieve integer val`() {
        val commitData = commitGateway.fetchCommitData("HyunsooZo","2024-11-14")
        assertTrue(commitData >= 0)
        println(commitData)
    }
}

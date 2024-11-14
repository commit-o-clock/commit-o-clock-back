package com.backend.commitoclock.infrastructure.gateway

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
    @DisplayName("유효한 githubId 에 대해 비어 있지 않은 맵을 검색해야 함")
    fun `fetchCommitData should retrieve non-empty map for valid username`() {
        val commitData = commitGateway.fetchCommitData("HyunsooZo")
        assertTrue(commitData.isNotEmpty())
        println(commitData.values)
    }
}

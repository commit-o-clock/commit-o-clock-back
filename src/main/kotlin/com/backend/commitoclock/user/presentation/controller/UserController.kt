package com.backend.commitoclock.user.presentation.controller

import com.backend.commitoclock.shared.model.CommitOClockResponse
import com.backend.commitoclock.user.application.service.read.UserReadService
import com.backend.commitoclock.user.application.service.UserModificationService
import com.backend.commitoclock.user.application.service.UserRegistrationService
import com.backend.commitoclock.user.presentation.payload.UserInformationResponse
import com.backend.commitoclock.user.presentation.payload.UserModificationRequest
import com.backend.commitoclock.user.presentation.payload.UserRegistrationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User")
@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userReadService: UserReadService,
    private val userRegistrationService: UserRegistrationService,
    private val userModificationService: UserModificationService
) {
    @GetMapping("/github-id/{githubId}")
    @Operation(summary = "Duplication check for GitHub ID")
    fun checkDuplication(
        @PathVariable githubId: String
    ): ResponseEntity<CommitOClockResponse<Boolean>> {
        val isAvailable = !userReadService.checkDuplication(githubId)
        return ResponseEntity.ok(CommitOClockResponse(message = isAvailable))
    }

    @PostMapping
    @Operation(summary = "Register a new user")
    fun registerUser(
        @RequestBody request: UserRegistrationRequest
    ): ResponseEntity<Void> {
        userRegistrationService.registerUser(request.toCommand())
        return ResponseEntity.ok().build()
    }

    @PatchMapping("/users/{userId}")
    @Operation(summary = "Update user information")
    fun updateUser(
        @PathVariable userId: String,
        @RequestBody request: UserModificationRequest
    ): ResponseEntity<Void> {
        userModificationService.updateUser(userId, request.toCommand())
        return ResponseEntity.ok().build()
    }

    @GetMapping("/github/{githubId}")
    @Operation(summary = "Get user information by GitHub ID")
    fun getUserByGithubId(
        @PathVariable githubId: String
    ): ResponseEntity<CommitOClockResponse<UserInformationResponse>> {
        val userInfo = userReadService.getUserByGithubId(githubId)
        return ResponseEntity.ok(CommitOClockResponse(message = userInfo.toResponse()))
    }
}
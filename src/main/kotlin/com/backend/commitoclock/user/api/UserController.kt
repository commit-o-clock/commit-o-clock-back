package com.backend.commitoclock.user.api

import com.backend.commitoclock.shared.model.CommitOClockResponse
import com.backend.commitoclock.user.api.payload.UserModificationRequest
import com.backend.commitoclock.user.api.payload.UserRegistrationRequest
import com.backend.commitoclock.user.service.UserInquiryService
import com.backend.commitoclock.user.service.UserModificationService
import com.backend.commitoclock.user.service.UserRegistrationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User")
@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userInquiryService: UserInquiryService,
    private val userRegistrationService: UserRegistrationService,
    private val userModificationService: UserModificationService
) {
    @GetMapping("/github-id/{githubId}")
    @Operation(summary = "Duplication check for GitHub ID")
    fun checkDuplication(
        @PathVariable githubId: String
    ): ResponseEntity<CommitOClockResponse<Boolean>> {
        val isAvailable = !userInquiryService.checkDuplication(githubId)
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
}
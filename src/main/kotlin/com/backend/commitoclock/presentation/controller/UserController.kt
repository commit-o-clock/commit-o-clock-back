package com.backend.commitoclock.presentation.controller

import com.backend.commitoclock.application.service.UserInquiryService
import com.backend.commitoclock.application.service.UserRegistrationService
import com.backend.commitoclock.presentation.model.CommitOClockResponse
import com.backend.commitoclock.presentation.model.UserRegistrationRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "User")
@RequestMapping("/api/v1/users")
@RestController
class UserController(
    private val userInquiryService: UserInquiryService,
    private val userRegistrationService: UserRegistrationService
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




}
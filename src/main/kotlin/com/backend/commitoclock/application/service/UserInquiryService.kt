package com.backend.commitoclock.application.service

import com.backend.commitoclock.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserInquiryService(
    private val userRepository: UserRepository
) {
    fun findAllUsers() = userRepository.findAll()
    fun getUser(userId: String) = userRepository.findById(userId)
    fun checkDuplication(githubId: String): Boolean {
        return userRepository.isExist(githubId)
    }
}

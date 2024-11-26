package com.backend.commitoclock.user.service

import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import com.backend.commitoclock.user.service.result.UserInformationResult
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
    fun getTargetUsers(currentHour: Int): List<User> {
        return userRepository.findAllByPreferredTime(currentHour)
    }

    fun getUserByGithubId(githubId: String): UserInformationResult {
        return userRepository.findByGithubId(githubId).let {
            UserInformationResult(
                id = it.id,
                username = it.username,
                githubId = it.githubId,
                country = it.country,
                countryCode = it.countryCode,
                lastCommitDate = it.lastCommitDate,
                commitData = it.commitData,
                notificationPreferences = it.notificationPreferences
            )
        }
    }
}

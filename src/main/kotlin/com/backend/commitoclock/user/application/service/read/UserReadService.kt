package com.backend.commitoclock.user.application.service.read

import com.backend.commitoclock.user.application.dto.result.UserInformationResult
import com.backend.commitoclock.user.domain.model.User
import com.backend.commitoclock.user.domain.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserReadService(
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun findAllUsers() = userRepository.findAll()

    @Transactional(readOnly = true)
    fun getUser(userId: String) = userRepository.findById(userId)

    @Transactional(readOnly = true)
    fun checkDuplication(githubId: String): Boolean {
        return userRepository.isExist(githubId)
    }

    @Transactional(readOnly = true)
    fun getTargetUsers(currentHour: Int): List<User> {
        return userRepository.findAllByPreferredTime(currentHour)
    }

    @Transactional(readOnly = true)
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

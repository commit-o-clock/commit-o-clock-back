package com.backend.commitoclock.commit.infra.concurrent

import com.backend.commitoclock.user.domain.model.User

interface UncommitUserQueue {
    fun add(user: User)
    fun poll(): User?
    fun isEmpty(): Boolean
}
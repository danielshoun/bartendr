package io.bartendr.bartendr.repositories

import io.bartendr.bartendr.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
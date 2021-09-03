package io.bartendr.bartendr.repositories

import io.bartendr.bartendr.models.EmailVerToken
import org.springframework.data.jpa.repository.JpaRepository

interface EmailVerTokenRepository : JpaRepository<EmailVerToken, Long> {
    fun findByToken(token: String): EmailVerToken?
}
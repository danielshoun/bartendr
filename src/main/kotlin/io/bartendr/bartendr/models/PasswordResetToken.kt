package io.bartendr.bartendr.models

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "password_reset_tokens")
class PasswordResetToken(
    @OneToOne @JoinColumn(name = "user_id") val user: User,
    @Column(name = "token") val token: String = UUID.randomUUID().toString()
) : BaseEntity() {

    override fun hashCode(): Int {
        return (id?.toInt() ?: -1) * (user.hashCode() + token.hashCode())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (other is PasswordResetToken) return user.id == other.user.id && token == other.token
        return false
    }
}
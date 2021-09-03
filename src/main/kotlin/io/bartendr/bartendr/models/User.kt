package io.bartendr.bartendr.models

import javax.persistence.*


@Entity
@Table(name = "users")
class User(
    @Column(name = "email") var email: String,
    @Column(name = "password") var password: String = "",
    @Column(name = "enabled") var enabled: Boolean = false,
    @Column(name = "first_name") var firstName: String,
    @Column(name = "last_name") var lastName: String
) : BaseEntity() {

    override fun hashCode(): Int {
        return (id?.toInt() ?: -1) *
                (email.hashCode() +
                        password.hashCode() +
                        enabled.hashCode() +
                        firstName.hashCode() +
                        lastName.hashCode()
                        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if(other is User) return email == other.email
        return false
    }

    override fun toString(): String {
        return "User [email=$email]"
    }
}
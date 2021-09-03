package io.bartendr.bartendr.models

import javax.persistence.*


@Entity
@Table(name = "users")
class User(
    @Column(name = "email") var email: String,
    @Column(name = "password") var password: String = "",
    @Column(name = "enabled") var enabled: Boolean = true,
    @Column(name = "first_name") var firstName: String,
    @Column(name = "last_name") var lastName: String
) : BaseEntity() {

//    @Column(name = "email")
//    var email: String? = null
//        private set
//
//    @Column(name = "password")
//    var password: String? = null
//
//    @Column(name = "enabled")
//    var enabled = false
//
//    @Column(name = "first_name")
//    var firstName: String? = null
//
//    @Column(name = "last_name")
//    var lastName: String? = null

//    constructor() : this()
//    constructor(email: String?, firstName: String?, lastName: String?) : super() {
//        this.email = email
//        this.firstName = firstName
//        this.lastName = lastName
//    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + email.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null) {
            return false
        }
        if (javaClass != other.javaClass) {
            return false
        }
        val user = other as User
        return email == user.email
    }

    override fun toString(): String {
        return "User [email=$email]"
    }
}
package io.bartendr.bartendr.models.dtos

import io.bartendr.bartendr.models.User

class UserDto(user: User) {
    val id: Long? = user.id
    val firstName: String = user.firstName
    val lastName: String = user.lastName
}

class SelfUserDto(user: User) {
    val id: Long? = user.id
    val firstName: String = user.firstName
    val lastName: String = user.lastName
    val email: String = user.email
}
package io.bartendr.bartendr.config.security

import io.bartendr.bartendr.models.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class UserPrincipal(val user: User) : UserDetails {
    override fun getUsername(): String? {
        return user.email
    }

    override fun getPassword(): String? {
        return user.password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return mutableListOf<GrantedAuthority>(SimpleGrantedAuthority("USER"))
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return user.enabled
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + user.hashCode()
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
        val other = other as UserPrincipal
        if (user != other.user) {
            return false
        }
        return true
    }
}
package io.bartendr.bartendr.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException

class AuthProcessingFilter :
    AbstractAuthenticationProcessingFilter(AntPathRequestMatcher("/api/v1/auth/login", "POST")) {
    @Throws(AuthenticationException::class)
    override fun attemptAuthentication(
        request: HttpServletRequest, response: HttpServletResponse
    ): Authentication {
        val username: String
        val password: String
        try {
            val requestMap: MutableMap<*, *>? = ObjectMapper().readValue(
                request.inputStream,
                MutableMap::class.java
            )
            if (requestMap != null) {
                username = requestMap["email"] as String
                password = requestMap["password"] as String
                request.setAttribute("rememberMe", requestMap["rememberMe"])
            } else {
                username = ""
                password = ""
                request.setAttribute("rememberMe", false)
            }
        } catch (e: IOException) {
            throw AuthenticationServiceException(e.message, e)
        }
        val authRequest = UsernamePasswordAuthenticationToken(
            username, password
        )
        return authenticationManager.authenticate(authRequest)
    }
}
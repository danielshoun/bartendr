package io.bartendr.bartendr.config.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.bartendr.bartendr.models.dtos.SelfUserDto
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

open class AuthSuccessHandler(private val jwtUtil: JwtUtil) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val principal: UserPrincipal = authentication.principal as UserPrincipal
        val token: String = jwtUtil.generateToken(principal.user)
        val cookie = Cookie("TOKEN", token)
        cookie.isHttpOnly = true
        if (request.getAttribute("rememberMe") as Boolean) {
            cookie.maxAge = 604800
        }
        response.addCookie(cookie)

        val jsonString: String = ObjectMapper().writeValueAsString(SelfUserDto(principal.user))
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.print(jsonString)
        response.writer.flush()
    }
}
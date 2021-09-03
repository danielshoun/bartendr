package io.bartendr.bartendr.config.security

import io.bartendr.bartendr.models.User
import io.bartendr.bartendr.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtFilter(val jwtUtil: JwtUtil) : OncePerRequestFilter() {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var cookie: Cookie? = null
        if (request.cookies != null) {
            cookie = request.cookies.find { it.name.equals("TOKEN") }
        }
        if (cookie == null) {
            filterChain.doFilter(request, response)
            return
        }
        val token: String = cookie.value
        if (!jwtUtil.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }

        val user: User? = userRepository.findByEmail(jwtUtil.getEmail(token))
        if(user == null) {
            filterChain.doFilter(request, response)
            return
        }
        val principal = UserPrincipal(user)
        val authentication = UsernamePasswordAuthenticationToken(principal, null, principal.authorities)
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
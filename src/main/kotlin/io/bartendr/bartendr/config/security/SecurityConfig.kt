package io.bartendr.bartendr.config.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import javax.sql.DataSource


@EnableWebSecurity
@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {
    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var appUserDetailsService: AppUserDetailsService

    @Autowired
    lateinit var jwtUtil: JwtUtil

    @Autowired
    lateinit var jwtFilter: JwtFilter

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(appUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .jdbcAuthentication()
            .usersByUsernameQuery("SELECT email AS username, password AS CREDENTIALS, enabled FROM users WHERE email = ?")
            .dataSource(dataSource)
    }

    override fun configure(http: HttpSecurity) {
        val authProcessingFilter = AuthProcessingFilter()
        authProcessingFilter.setAuthenticationManager(authenticationManager())
        authProcessingFilter.setAuthenticationSuccessHandler(AuthSuccessHandler(jwtUtil))
        http
            .csrf { csrf: CsrfConfigurer<HttpSecurity?> ->
                csrf
                    .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            }
            .addFilterAt(
                authProcessingFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/api/v1/auth/login").permitAll()
            .antMatchers(HttpMethod.POST, "/api/v1/auth/register").permitAll()
            .antMatchers(HttpMethod.GET, "/api/v1/auth/self").authenticated()
    }
}
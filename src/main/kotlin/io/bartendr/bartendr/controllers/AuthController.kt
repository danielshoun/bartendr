package io.bartendr.bartendr.controllers

import io.bartendr.bartendr.config.security.UserPrincipal
import io.bartendr.bartendr.forms.ForgotPasswordForm
import io.bartendr.bartendr.forms.RegisterNewUserForm
import io.bartendr.bartendr.forms.ResetPasswordForm
import io.bartendr.bartendr.models.dtos.SelfUserDto
import io.bartendr.bartendr.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
@Validated
class AuthController {
    @Autowired
    lateinit var authService: AuthService

    @GetMapping("/self")
    @ResponseBody
    fun getSelf(authentication: Authentication): ResponseEntity<SelfUserDto> {
        val principal: UserPrincipal = authentication.principal as UserPrincipal
        return ResponseEntity.ok(SelfUserDto(principal.user))
    }

    @PostMapping("/register")
    @ResponseBody
    fun registerNewUser(@Valid @RequestBody registerNewUserForm: RegisterNewUserForm): ResponseEntity<Any> {
        return authService.registerNewUser(registerNewUserForm)
    }

    @PostMapping("/email-ver/{token}")
    @ResponseBody
    fun verifyEmail(@PathVariable(required = true, name = "token") token: String): ResponseEntity<String> {
        return authService.verifyEmail(token)
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    fun forgotPassword(
        @Valid @RequestBody forgotPasswordForm: ForgotPasswordForm
    ): ResponseEntity<String> {
        return authService.forgotPassword(forgotPasswordForm)
    }

    @PostMapping("/reset-password/{token}")
    @ResponseBody
    fun resetPassword(
        @PathVariable(required = true, name = "token") token: String,
        @Valid @RequestBody resetPasswordForm: ResetPasswordForm
    ): ResponseEntity<String> {
        return authService.resetPassword(token, resetPasswordForm)
    }

    @DeleteMapping("/logout")
    @ResponseBody
    fun logout(response: HttpServletResponse): ResponseEntity<String> {
        return authService.logout(response)
    }
}
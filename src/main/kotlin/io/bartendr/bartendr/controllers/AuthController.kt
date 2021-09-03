package io.bartendr.bartendr.controllers

import io.bartendr.bartendr.config.security.UserPrincipal
import io.bartendr.bartendr.forms.RegisterNewUserForm
import io.bartendr.bartendr.models.User
import io.bartendr.bartendr.models.dtos.SelfUserDto
import io.bartendr.bartendr.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/auth")
@Validated
class AuthController {
    @Autowired
    lateinit var authService: AuthService

    @GetMapping("/self")
    @ResponseBody
    fun getSelf(authentication: Authentication): SelfUserDto {
        val principal: UserPrincipal = authentication.principal as UserPrincipal
        return SelfUserDto(principal.user)
    }

    @PostMapping("/register")
    @ResponseBody
    fun registerNewUser(@Valid @RequestBody registerNewUserForm: RegisterNewUserForm, bindingResult: BindingResult): SelfUserDto {
        return authService.registerNewUser(registerNewUserForm, bindingResult)
    }
}
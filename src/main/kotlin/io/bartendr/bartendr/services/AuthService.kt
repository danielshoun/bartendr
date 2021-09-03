package io.bartendr.bartendr.services

import io.bartendr.bartendr.forms.RegisterNewUserForm
import io.bartendr.bartendr.models.EmailVerToken
import io.bartendr.bartendr.models.User
import io.bartendr.bartendr.models.dtos.SelfUserDto
import io.bartendr.bartendr.repositories.EmailVerTokenRepository
import io.bartendr.bartendr.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.BindingResult

@Service
class AuthService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var emailVerTokenRepository: EmailVerTokenRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun registerNewUser(registerNewUserForm: RegisterNewUserForm, bindingResult: BindingResult): SelfUserDto {
        val user = User(
            email = registerNewUserForm.email.lowercase(),
            firstName = registerNewUserForm.firstName,
            lastName = registerNewUserForm.lastName
        )
        user.password = passwordEncoder.encode(registerNewUserForm.password)
        userRepository.save(user)

        val emailVerToken = EmailVerToken(user)
        emailVerTokenRepository.save(emailVerToken)
        return SelfUserDto(user)
    }
}
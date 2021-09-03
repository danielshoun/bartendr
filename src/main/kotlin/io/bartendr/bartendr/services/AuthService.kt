package io.bartendr.bartendr.services

import io.bartendr.bartendr.forms.ForgotPasswordForm
import io.bartendr.bartendr.forms.RegisterNewUserForm
import io.bartendr.bartendr.forms.ResetPasswordForm
import io.bartendr.bartendr.models.EmailVerToken
import io.bartendr.bartendr.models.PasswordResetToken
import io.bartendr.bartendr.models.User
import io.bartendr.bartendr.models.dtos.SelfUserDto
import io.bartendr.bartendr.repositories.EmailVerTokenRepository
import io.bartendr.bartendr.repositories.PasswordResetTokenRepository
import io.bartendr.bartendr.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService {
    @Value("\${env.type}")
    lateinit var envType: String

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var emailVerTokenRepository: EmailVerTokenRepository

    @Autowired
    lateinit var passwordResetTokenRepository: PasswordResetTokenRepository

    @Autowired
    lateinit var emailService: EmailService

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    fun registerNewUser(registerNewUserForm: RegisterNewUserForm): SelfUserDto {
        val user = User(
            email = registerNewUserForm.email.lowercase(),
            firstName = registerNewUserForm.firstName,
            lastName = registerNewUserForm.lastName
        )
        user.password = passwordEncoder.encode(registerNewUserForm.password)
        userRepository.save(user)

        val emailVerToken = EmailVerToken(user)
        emailVerTokenRepository.save(emailVerToken)
        emailService.sendHtmlMessage(
            to = user.email,
            subject = "[Bartendr.io] Account Activation",
            htmlBody = """
                Thank you for registering with Bartendr.io! Your account activation link is below.
                
                ${if (envType == "production") "https://bartendr.io" else "http://localhost:8080"}/email-ver/${emailVerToken.token}
            """.trimIndent()
        )

        return SelfUserDto(user)
    }

    fun verifyEmail(token: String): String {
        val emailVerToken = emailVerTokenRepository.findByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Token not found.")
        emailVerToken.user.enabled = true
        userRepository.save(emailVerToken.user)
        emailVerTokenRepository.delete(emailVerToken)
        return "Verified."
    }

    fun forgotPassword(forgotPasswordForm: ForgotPasswordForm): String {
        val user: User = userRepository.findByEmail(forgotPasswordForm.email)!!
        val passwordResetToken = PasswordResetToken(user)
        passwordResetTokenRepository.save(passwordResetToken)

        emailService.sendHtmlMessage(
            to = user.email,
            subject = "[Bartendr.io] Account Recovery",
            htmlBody = """
                Please use the link below to reset your password.
                
                ${if (envType == "production") "https://bartendr.io" else "http://localhost:8080"}/reset-password/${passwordResetToken.token}
            """.trimIndent()
        )
        return "Password reset link sent."
    }

    fun resetPassword(token: String, resetPasswordForm: ResetPasswordForm): String {
        val passwordResetToken = passwordResetTokenRepository.findByToken(token)
            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Token not found.")
        passwordResetToken.user.password = passwordEncoder.encode(resetPasswordForm.password)
        userRepository.save(passwordResetToken.user)
        passwordResetTokenRepository.delete(passwordResetToken)
        return "Password changed successfully."
    }
}
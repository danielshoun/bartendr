package io.bartendr.bartendr.forms.constraints

import io.bartendr.bartendr.repositories.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@MustBeDocumented
@Constraint(validatedBy = [EmailInUseValidator::class])
annotation class EmailInUse(
    val message: String = "No user found with email.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class EmailInUseValidator : ConstraintValidator<EmailInUse, String> {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value?.let { userRepository.findByEmail(it) } !== null) {
            return true
        }
        return false
    }
}
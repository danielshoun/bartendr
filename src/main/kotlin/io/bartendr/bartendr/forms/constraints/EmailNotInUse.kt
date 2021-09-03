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
@Constraint(validatedBy = [EmailNotInUseValidator::class])
annotation class EmailNotInUse(
    val message: String = "Email already in use.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class EmailNotInUseValidator : ConstraintValidator<EmailNotInUse, String> {
    @Autowired
    lateinit var userRepository: UserRepository

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value?.let { userRepository.findByEmail(it) } !== null) {
            return false
        }
        return true
    }
}
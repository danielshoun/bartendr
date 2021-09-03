package io.bartendr.bartendr.forms

import io.bartendr.bartendr.forms.constraints.EmailInUse
import javax.validation.constraints.NotNull

data class ForgotPasswordForm(
    @field:NotNull @field:EmailInUse val email: String
)
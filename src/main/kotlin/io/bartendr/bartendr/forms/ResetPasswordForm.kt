package io.bartendr.bartendr.forms

import javax.validation.constraints.Size

data class ResetPasswordForm(
    @field:Size(min = 8) val password: String
)
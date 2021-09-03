package io.bartendr.bartendr.errors

class ValidationErrorResponse(
    val violations: MutableList<Violation> = mutableListOf()
)

class Violation(
    val fieldName: String,
    val message: String
)
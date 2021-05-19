package data_design
import arrow.core.*
import kotlin.jvm.JvmInline

typealias Name = ValueClasses.NonBlankString

object ValueClasses {
    @JvmInline
    value class NonBlankString private constructor(val value: String) {

        companion object {
            fun make(value: String): Validated<Error, NonBlankString> =
                if (value.isBlank()) Error.IS_BLANK.invalid() else NonBlankString(value).valid()
        }

        enum class Error {
            IS_BLANK
        }
    }

    @JvmInline
    value class Age private constructor(val value: Int) {

        companion object {
            fun make(value: Int): Validated<Error, Age> =
                if (value < 0) Error.IS_NEGATIVE.invalid() else Age(value).valid()
        }

        enum class Error {
            IS_NEGATIVE
        }
    }

    data class User private constructor(val name: Name, val age: Age) {
        companion object {
            fun make(name: String, age: Int): Validated<NonEmptyList<Error>, User> =
                validateName(name).toValidatedNel()
                    .zip(validateAge(age).toValidatedNel()) { validName, validAge ->
                        User(validName, validAge)
                    }

            fun validateName(name: String): Validated<Error, NonBlankString> =
                NonBlankString.make(name).mapLeft { error -> Error.EMPTY_NAME }

            fun validateAge(age: Int): Validated<Error, Age> =
                when (val validatedAge = Age.make(age)) {
                    is Validated.Valid ->
                        if (validatedAge.value.value < 18) Error.TOO_YOUNG.invalid()
                        else validatedAge
                    is Validated.Invalid ->
                        Error.TOO_YOUNG.invalid()
                }

            enum class Error {
                EMPTY_NAME, TOO_YOUNG
            }
        }
    }

    val s: NonBlankString = TODO()
    val x = s.value.substring(5)
}
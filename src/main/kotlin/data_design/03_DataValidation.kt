package data_design

import arrow.core.*

object NaiveApproach {
    data class User(val name: String, val age: Int) {
        init {
            assert(name.isNotBlank())
            assert(age >= 18)
        }
    }


    fun tooYoung(): User = User("Bilal", 16) // throws Exception 🤮
    fun noName(): User   = User("", 33)      // throws Exception 🤮


    /** --
    “exceptions are, as their name implies, to be used only for exceptional conditions;
    they should never be used for ordinary control flow.”
    Joshua Blooch, Effective Java - Second Edition, Addison-Wesley, 2008, Chapter 9 “Exceptions”
     */
}

object SmartApproach {
    /*--
    Use the @noCopy annotation for real world applications.
    Else copy can create invalid data structures.
    See https://github.com/AhmedMourad0/no-copy
     */
    data class User private constructor(val name: String, val age: Int) {
        companion object {
            fun make(name: String, age: Int): Validated<NonEmptyList<Error>, User> =
                validateName(name).toValidatedNel().zip(validateAge(age).toValidatedNel()) { validName, validAge ->
                    User(validName, validAge)
                }

            fun validateName(name: String): Validated<Error, String> =
                if (name.isBlank()) Validated.Invalid(Error.EMPTY_NAME) else Validated.Valid(name)

            fun validateAge(age: Int): Validated<Error, Int> =
                if (age < 18) Validated.Invalid(Error.TOO_YOUNG) else Validated.Valid(age)

            enum class Error {
                EMPTY_NAME, TOO_YOUNG
            }
        }
    }


    val tooYoung = User.make("Bilal", 3)
    val noName   = User.make("", 33)
    val valid    = User.make("Nabil", 33)

    val noNameAsString = noName.fold({"the error was $it"}, {"the success value was $it"})
    val validAsString  = valid.fold({"the error was $it"}, {"the success value was $it"})
}

object SmartApproachAlternative {
    data class NonBlankString private constructor(val value: String) {

        companion object {
            fun make(value: String): Validated<Error, NonBlankString> =
                if (value.isBlank()) Error.IS_BLANK.invalid() else NonBlankString(value).valid()
        }

        enum class Error {
            IS_BLANK
        }
    }

    data class Age private constructor(val value: Int) {

        companion object {
            fun make(value: Int): Validated<Error, Age> =
                if (value < 0) Error.IS_NEGATIVE.invalid() else Age(value).valid()
        }

        enum class Error {
            IS_NEGATIVE
        }
    }

    data class User private constructor(val name: NonBlankString, val age: Age) {
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

}

fun main() {
    println("--- smart constructor ---")
    println("tooYoung = ${SmartApproach.tooYoung}")
    println("noName = ${SmartApproach.noName}")
    println("valid = ${SmartApproach.valid}")
    println("noNameAsString = ${SmartApproach.noNameAsString}")
    println("validAsString = ${SmartApproach.validAsString}")
}

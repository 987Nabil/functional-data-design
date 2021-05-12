package data_design

import zio.prelude._


object SmartApproachAlternative {
  case class NonBlankString private(val value: String)

  object NonBlankString {
    def make(value: String): Validation[Error, NonBlankString] =
      if (value.isBlank()) Validation.fail(Error.IsBlank)
      else Validation.succeed(NonBlankString(value))

    enum Error {
      case IsBlank
    }
  }


  case class Age private(val value: Int)

  object Age {
    def make(value: Int): Validation[Error, Age] =
      if (value < 0) Validation.fail(Error.IsNegative) else Validation.succeed(Age(value))


    enum Error {
      case IsNegative
    }
  }

  case class User private(name: NonBlankString, age: Age)

  object User {
    def make(name: String, age: Int): Validation[Error, User] =
      Validation.validateWith(
        validateName(name),
        validateAge(age)
      )((name, age) => User(name, age))


    def validateName(name: String): Validation[Error, NonBlankString] =
      NonBlankString.make(name).mapError(error => Error.EmptyName)

    def validateAge(age: Int): Validation[Error, Age] =
      Age.make(age) match {
        case valid @ Validation.Success(_, age) if (age.value >= 18) =>
          valid
        case _ =>
          Validation.fail(Error.TooYoung)

      }

    enum Error {
      case EmptyName, TooYoung
    }
  }

  /*
  val tooYoung = User.make("Bilal", 3)
  val noName   = User.make("", 33)
  val valid    = User.make("Nabil", 33)

  val noNameAsString = noName.fold(e => s"the error was $e", s => s"the success value was $s")
  val validAsString  = valid.fold(e => s"the error was $e", s => s"the success value was $s")
   */

}

/*
@main
def dataValidation = {
  println(s"--- smart constructor ---")
  println(s"tooYoung = ${SmartApproachAlternative.tooYoung}")
  println(s"noName = ${SmartApproachAlternative.noName}")
  println(s"valid = ${SmartApproachAlternative.valid}")
  println(s"noNameAsString = ${SmartApproachAlternative.noNameAsString}")
  println(s"validAsString = ${SmartApproachAlternative.validAsString}")
}*/

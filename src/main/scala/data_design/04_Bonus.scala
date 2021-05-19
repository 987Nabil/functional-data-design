package data_design

import zio.prelude._
import zio.prelude.newtypes._
import zio.test.Assertion

object NewTypesPrelude {

  val nonBlankAssertion =
    Assertion.assertion[String]("non blank string")()(!_.isBlank)

  object NonBlankString extends SubtypeSmart[String](nonBlankAssertion)

  type NonBlankString = NonBlankString.type

  object Age extends SubtypeSmart[Int](Assertion.isPositive)

  type Age = Age.type

  case class User private(name: NonBlankString, age: Age)

  def stringLength(s:String) = s.length

  @main
  def ntPrelude = {
    println(NonBlankString.make("Hello"))
    NonBlankString.make("Hello")
      .fold( _ => ???, nbs => println(s"length is ${stringLength(nbs)}"))
    println(NonBlankString.make(" "))
    println(NonBlankString.make(""))
  }

}

object NewTypesScala3 {
  object Types {
    opaque type NonBlankString <: String = String

    object NonBlankString {
      def make(value: String): Validation[Error, NonBlankString] =
        if (value.isBlank()) Validation.fail(Error.IsBlank)
        else Validation.succeed(value)

      val example: NonBlankString = "Example"

      enum Error {
        case IsBlank
      }

    }
  }
  val s: Types.NonBlankString = Types.NonBlankString.example
  val x = s.substring(5)

  def stringLength(s: String) = s.length


  @main
  def ntScala3 = {
    import Types._
    println(NonBlankString.make("Hello"))
    NonBlankString.make("Hello")
      .fold( _ => ???, nbs => println(s"length is ${stringLength(nbs)}"))
    println(NonBlankString.make(" "))
    println(NonBlankString.make(""))
  }

}
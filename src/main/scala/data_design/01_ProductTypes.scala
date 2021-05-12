package data_design

object ProductTypesSimple {
  val tuple: (String, Int)          = ("Nabil", 33)
  val triple: (String, Int, String) = ("Nabil", 33, "Rewe Digital")

  //Scala tuples of arbitry length
  val tupleCombined: (String, String, Int, String, Int, String) =
    "Combined" *: tuple ++ triple

  val myName = tuple._1 // "Nabil"
}

object ProductTypesAdvanced {
  // case vs data
  case class Person(name: String, age: Int, workingPlace: String)

  val nabil: Person = Person("Nabil", 33, "Rewe Digital")
  val myName        = nabil.name // "Nabil"
}

@main
def productTypes =
  //multiple mains via annotation with arbitrary namens. Names must be unique per package
  println("--- simple ---")
  // String interpolation -> s""
  println(s"pair = ${ProductTypesSimple.tuple}")
  println(s"triple = ${ProductTypesSimple.triple}")
  println(s"triple = ${ProductTypesSimple.tupleCombined}")
  println(s"myName = ${ProductTypesSimple.myName}")

  println("--- advanced ---")
  println(s"nabil = ${ProductTypesAdvanced.nabil}")
  println(s"myName = ${ProductTypesAdvanced.myName}")
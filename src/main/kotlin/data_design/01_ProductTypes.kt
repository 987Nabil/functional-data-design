package data_design

/** Product types
 Given types A and B their product type is A x B
    -> Possible instances are all combination of A and B
 Other name: Record Type
 Similar to classical database tables (one column for each type)
 */
object ProductTypesSimple {
    val pair: Pair<String, Int>             = Pair("Nabil", 33)
    val triple: Triple<String, Int, String> = Triple("Nabil", 33, "Rewe Digital")

    val myName = pair.first // "Nabil"
}

object ProductTypesAdvanced {
    data class Person(val name: String, val age: Int, val workingPlace: String)

    val nabil: Person = Person("Nabil", 33, "Rewe Digital")
    val myName        = nabil.name // "Nabil"
}

fun main() {

    println("--- simple ---")
    println("pair = ${ProductTypesSimple.pair}")
    println("triple = ${ProductTypesSimple.triple}")
    println("myName = ${ProductTypesSimple.myName}")

    println("--- advanced ---")
    println("nabil = ${ProductTypesAdvanced.nabil}")
    println("myName = ${ProductTypesAdvanced.myName}")

}
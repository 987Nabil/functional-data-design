package data_design

import arrow.core.Either

/**
Given types A and B their sum type is A + B
-> Possible instances are the instances of A or the instances of B
 */

typealias PaymentMethod = Either<SumTypesSimple.CreditCard, SumTypesSimple.WireTransfer>

object SumTypesSimple {

    data class CreditCard(val number: String)
    data class WireTransfer(val iban: String)

    val sampleCC = CreditCard("4242424242424242")
    val sampleWT = WireTransfer("DE02120300000000202051")

    val cc: PaymentMethod = Either.Left(sampleCC)   // sampleCC.left()
    val wt: PaymentMethod = Either.Right(sampleWT)  // sampleWT.right()

    fun showPaymentMethod(pm: PaymentMethod) =
        when (pm) {
            is Either.Left<CreditCard>    -> "CreditCard with number ${pm.value.number}"
            is Either.Right<WireTransfer> -> "WireTransfer with iban ${pm.value.iban}"
        }
}

object SumTypesAdvanced {
    //can not be extended only by inner classes
    sealed class PaymentMethod {
        data class CreditCard(val number: String) : PaymentMethod()
        data class WireTransfer(val iban: String) : PaymentMethod()
    }


    val sampleCC: PaymentMethod.CreditCard   = PaymentMethod.CreditCard("4242424242424242")
    val sampleWT: PaymentMethod.WireTransfer = PaymentMethod.WireTransfer("DE02120300000000202051")

    val cc: PaymentMethod = sampleCC
    val wt: PaymentMethod = sampleWT

    fun showPaymentMethod(pm: PaymentMethod) =
        when (pm) {
            is PaymentMethod.CreditCard   -> "CreditCard with number ${pm.number}"
            is PaymentMethod.WireTransfer -> "WireTransfer with iban ${pm.iban}"
        }

}

fun main() {
    println("--- simple ---")
    println("cc = ${SumTypesSimple.cc}")
    println("wt = ${SumTypesSimple.wt}")
    println("show cc: ${SumTypesSimple.showPaymentMethod(SumTypesSimple.cc)}")
    println("show wt: ${SumTypesSimple.showPaymentMethod(SumTypesSimple.wt)}")

    println("--- advanced ---")
    println("cc = ${SumTypesAdvanced.cc}")
    println("wt = ${SumTypesAdvanced.wt}")
    println("show cc: ${SumTypesAdvanced.showPaymentMethod(SumTypesAdvanced.cc)}")
    println("show wt: ${SumTypesAdvanced.showPaymentMethod(SumTypesAdvanced.wt)}")
}



















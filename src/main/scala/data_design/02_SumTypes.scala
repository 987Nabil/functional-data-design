package data_design

package data_design

/** possible values |X| = |A| + |B| */
object SumTypesSimple:

  type PaymentMethod =
    Either[SumTypesSimple.CreditCard, SumTypesSimple.WireTransfer]

  case class CreditCard(val number: String)
  case class WireTransfer(val iban: String)

  val sampleCC = CreditCard("4242424242424242")
  val sampleWT = WireTransfer("DE02120300000000202051")

  val cc: PaymentMethod = Left(sampleCC)
  val wt: PaymentMethod = Right(sampleWT)

  def showPaymentMethod(pm: PaymentMethod) =
    pm match
      case Left(CreditCard(number))  => s"CreditCard with number $number"
      case Right(WireTransfer(iban)) => s"WireTransfer with iban $iban"

object SumTypesAdvanced:
  // can not be extended only in current file
  sealed trait PaymentMethod_Scala2

  object PaymentMethod_Scala2:
    case class CreditCard(val number: String) extends PaymentMethod_Scala2
    case class WireTransfer(val iban: String) extends PaymentMethod_Scala2

  // The Scala 3 way
  enum PaymentMethod:
    case CreditCard(number: String)
    case WireTransfer(iban: String)

  val sampleCC: PaymentMethod.CreditCard   = PaymentMethod.CreditCard("4242424242424242")
  val sampleWT: PaymentMethod.WireTransfer = PaymentMethod.WireTransfer("DE02120300000000202051")

  val cc: PaymentMethod = sampleCC
  val wt: PaymentMethod = sampleWT

  def showPaymentMethod(pm: PaymentMethod) =
    pm match
      case PaymentMethod.CreditCard(number) => s"CreditCard with number $number"
      case PaymentMethod.WireTransfer(iban) => s"WireTransfer with iban $iban"

@main
def sumTypes =
  println(s"--- simple ---")
  println(s"cc = ${SumTypesSimple.cc}")
  println(s"wt = ${SumTypesSimple.wt}")
  println(s"show cc: ${SumTypesSimple.showPaymentMethod(SumTypesSimple.cc)}")
  println(s"show wt: ${SumTypesSimple.showPaymentMethod(SumTypesSimple.wt)}")

  println(s"--- advanced ---")
  println(s"cc = ${SumTypesAdvanced.cc}")
  println(s"wt = ${SumTypesAdvanced.wt}")
  println(s"show cc: ${SumTypesAdvanced.showPaymentMethod(SumTypesAdvanced.cc)}")
  println(s"show wt: ${SumTypesAdvanced.showPaymentMethod(SumTypesAdvanced.wt)}")

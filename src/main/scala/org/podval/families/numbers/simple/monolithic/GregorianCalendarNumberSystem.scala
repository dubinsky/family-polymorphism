package org.podval.families.numbers.simple.monolithic

object GregorianCalendarNumberSystem extends CalendarNumberSystem {
  import NumberSystem.Raw

  protected final override type Number = GregorianCalendarNumber

  protected def createNumber(raw: Raw): Number = new GregorianCalendarNumber(raw)


  final class GregorianCalendarNumber(raw: Raw) extends AbstractCalendarNumber(raw) {
    def morningHours(value: Int): Number = ???
  }
}

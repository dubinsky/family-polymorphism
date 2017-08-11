package org.podval.families.numbers.simple.one

object GregorianCalendarNumberSystem extends CalendarNumberSystem {
  protected final override type Number = GregorianCalendarNumber

  protected def createNumber(raw: Raw): Number = new GregorianCalendarNumber(raw)

  final class GregorianCalendarNumber(raw: Raw) extends AbstractCalendarNumber(raw) {
  }
}

package org.podval.families.numbers.complex.one

object GregorianCalendarNumberSystem extends CalendarNumberSystem {
  protected final override type Point = GregorianCalendarPoint

  protected def createPoint(raw: Raw): Point = new GregorianCalendarPoint(raw)

  final class GregorianCalendarPoint(raw: Raw) extends CalendarPoint(raw) {
  }
}

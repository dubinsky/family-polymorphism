package org.podval.families.numbers.complex.monolithic

object GregorianCalendarNumberSystem extends CalendarNumberSystem {
  import NumberSystem.Raw

  protected final override type Point = GregorianCalendarPoint

  protected def createPoint(raw: Raw): Point = new GregorianCalendarPoint(raw)


  final class GregorianCalendarPoint(raw: Raw) extends CalendarPoint(raw) {
    def morningHours(value: Int): Point = ???
  }
}

package org.podval.families.numbers.complex.one

object GregorianCalendarNumberSystem extends CalendarNumberSystem {
  protected final override type Point = GregorianCalendarPoint

  protected def createPoint(raw: Raw): Point = new GregorianCalendarPoint(raw)

  final class GregorianCalendarPoint(raw: Raw) extends CalendarPoint(raw) {
    override protected def numberSystem: NumberSystem =
      GregorianCalendarNumberSystem.this

    // TODO can these be moved into AbstractNumber by some miracle?

    override protected def createPoint(raw: Raw): Point =
      GregorianCalendarNumberSystem.this.createPoint(raw)

    override protected def createInterval(raw: Raw): Interval =
      GregorianCalendarNumberSystem.this.createInterval(raw)
  }
}

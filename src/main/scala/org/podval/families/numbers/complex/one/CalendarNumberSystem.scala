package org.podval.families.numbers.complex.one

abstract class CalendarNumberSystem extends NumberSystem {

  protected final override type Interval = CalendarInterval

  protected def createInterval(raw: Raw): Interval = new CalendarInterval(raw)


  trait CalendarNumber extends Number {
    final def days: Int = digit(0)

    final def days(value: Int): SelfType = digit(0, value)

    final def hours: Int = digit(1)

    final def hours(value: Int): SelfType = digit(1, value)
  }


  abstract class CalendarPoint(raw: Raw)
    extends AbstractNumber(raw)
    with CalendarNumber
    with PointBase


  final class CalendarInterval(raw: Raw)
    extends AbstractNumber(raw)
    with CalendarNumber
    with IntervalBase
  {
    override protected def numberSystem: NumberSystem =
      CalendarNumberSystem.this

    // TODO can these be moved into AbstractNumber by some miracle?

    override protected def createPoint(raw: Raw): Point =
      CalendarNumberSystem.this.createPoint(raw)

    override protected def createInterval(raw: Raw): Interval =
      CalendarNumberSystem.this.createInterval(raw)
  }
}

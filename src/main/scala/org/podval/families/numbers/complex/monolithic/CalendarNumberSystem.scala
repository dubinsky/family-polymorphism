package org.podval.families.numbers.complex.monolithic

trait CalendarNumberSystem extends NumberSystem {
  import NumberSystem.Raw

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
}

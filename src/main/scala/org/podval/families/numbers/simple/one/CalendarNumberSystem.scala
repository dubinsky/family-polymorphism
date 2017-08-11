package org.podval.families.numbers.simple.one

abstract class CalendarNumberSystem extends NumberSystem {

  trait CalendarNumber extends NumberBase {
    final def days: Int = digit(0)

    final def days(value: Int): Number = digit(0, value)

    final def hours: Int = digit(1)

    final def hours(value: Int): Number = digit(1, value)
  }


  abstract class AbstractCalendarNumber(raw: Raw)
    extends AbstractNumber(raw)
    with NumberBase
  {
    final override protected def numberSystem: NumberSystem =
      CalendarNumberSystem.this

    // TODO can this be moved into AbstractNumber by some miracle?
    final override protected def create(raw: Raw): Number =
      CalendarNumberSystem.this.createNumber(raw)
  }
}

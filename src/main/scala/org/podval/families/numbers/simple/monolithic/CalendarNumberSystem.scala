package org.podval.families.numbers.simple.monolithic

trait CalendarNumberSystem extends NumberSystem {
  import NumberSystem.Raw

  trait CalendarNumber extends NumberBase {
    final def days: Int = digit(0)

    final def days(value: Int): Number = digit(0, value)

    final def hours: Int = digit(1)

    final def hours(value: Int): Number = digit(1, value)
  }


  abstract class AbstractCalendarNumber(raw: Raw)
    extends AbstractNumber(raw)
    with CalendarNumber
}

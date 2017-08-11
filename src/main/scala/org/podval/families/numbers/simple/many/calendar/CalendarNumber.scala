package org.podval.families.numbers.simple.many.calendar

import org.podval.families.numbers.simple.many.base.NumberBase

trait CalendarNumber[S <: CalendarNumberSystem[S]] extends NumberBase[S] { this: S#Number =>
  final def days: Int = digit(0)

  final def days(value: Int): S#Number = digit(0, value)

  final def hours: Int = digit(1)

  final def hours(value: Int): S#Number = digit(1, value)
}

package org.podval.families.numbers.simple.many.calendar

import org.podval.families.numbers.simple.many.base.AbstractNumber
import org.podval.families.numbers.simple.many.base.NumberSystem.Raw

abstract class AbstractCalendarNumber[S <: CalendarNumberSystem[S]](raw: Raw)
  extends AbstractNumber[S](raw)
  with CalendarNumber[S]
{
  this: S#Number =>
}

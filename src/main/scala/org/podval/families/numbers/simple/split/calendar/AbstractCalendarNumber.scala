package org.podval.families.numbers.simple.split.calendar

import org.podval.families.numbers.simple.split.base.AbstractNumber
import org.podval.families.numbers.simple.split.base.NumberSystem.Raw

abstract class AbstractCalendarNumber[S <: CalendarNumberSystem[S]](raw: Raw)
  extends AbstractNumber[S](raw)
  with CalendarNumber[S]
{
  this: S#Number =>
}

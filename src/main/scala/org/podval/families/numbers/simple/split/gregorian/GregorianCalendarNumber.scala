package org.podval.families.numbers.simple.split.gregorian

import org.podval.families.numbers.simple.split.base.NumberSystem.Raw
import org.podval.families.numbers.simple.split.calendar.AbstractCalendarNumber

// TODO has to be abstract and not final: it needs to be overridden in
//   GregorianCalendarNumberSystem.createNumber.
abstract class GregorianCalendarNumber(raw: Raw)
  extends AbstractCalendarNumber[GregorianCalendarNumberSystem](raw)
{ this: GregorianCalendarNumberSystem#Number =>

  def morningHours(value: Int): GregorianCalendarNumberSystem#Number = ???
}

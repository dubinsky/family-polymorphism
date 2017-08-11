package org.podval.families.numbers.simple.many.gregorian

import org.podval.families.numbers.simple.many.base.NumberSystem.Raw
import org.podval.families.numbers.simple.many.calendar.AbstractCalendarNumber

// TODO has to be abstract and not final: it needs to be overridden in
//   GregorianCalendarNumberSystem.createNumber.
abstract class GregorianCalendarNumber(raw: Raw)
  extends AbstractCalendarNumber[GregorianCalendarNumberSystem](raw)
{ this: GregorianCalendarNumberSystem#Number =>
}

package org.podval.families.numbers.simple.many.gregorian

import org.podval.families.numbers.simple.many.base.NumberSystem.Raw
import org.podval.families.numbers.simple.many.calendar.AbstractCalendarNumber

// TODO do I need the type parameter?
// TODO has to be abstract and not final: it needs to be overridden in
//   GregorianCalendarNumberSystem.createNumber.
abstract class GregorianCalendarNumber[S <: GregorianCalendarNumberSystem[S]](raw: Raw)
  extends AbstractCalendarNumber[S](raw)
{ this: S#Number =>
}

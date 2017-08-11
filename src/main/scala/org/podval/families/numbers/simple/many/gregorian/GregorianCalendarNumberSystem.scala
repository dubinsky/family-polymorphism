package org.podval.families.numbers.simple.many.gregorian

import org.podval.families.numbers.simple.many.base.NumberSystem.Raw
import org.podval.families.numbers.simple.many.calendar.CalendarNumberSystem

// TODO can't be final: object extends it.
class GregorianCalendarNumberSystem[S <: GregorianCalendarNumberSystem[S]]
  extends CalendarNumberSystem[S]
{ this: S =>
  final override type Number = GregorianCalendarNumber[S]

  def createNumber(raw: Raw): S#Number = new GregorianCalendarNumber[S](raw) {
    override def numberSystem: S = GregorianCalendarNumberSystem.this
  }
}


object GregorianCalendarNumberSystem
  extends GregorianCalendarNumberSystem[GregorianCalendarNumberSystem.type]

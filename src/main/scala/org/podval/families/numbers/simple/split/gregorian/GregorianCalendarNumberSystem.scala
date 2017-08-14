package org.podval.families.numbers.simple.split.gregorian

import org.podval.families.numbers.simple.split.base.NumberSystem.Raw
import org.podval.families.numbers.simple.split.calendar.CalendarNumberSystem

// TODO can't be final: object extends it.
class GregorianCalendarNumberSystem
  extends CalendarNumberSystem[GregorianCalendarNumberSystem]
{
  final override type Number = GregorianCalendarNumber

  def createNumber(raw: Raw): GregorianCalendarNumberSystem#Number =
    new GregorianCalendarNumber(raw) {
      override def numberSystem: GregorianCalendarNumberSystem =
        GregorianCalendarNumberSystem.this
    }
}


object GregorianCalendarNumberSystem extends GregorianCalendarNumberSystem

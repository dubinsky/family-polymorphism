package org.podval.families.numbers.simple.many.calendar

import org.podval.families.numbers.simple.many.base.NumberSystem

trait CalendarNumberSystem[S <: CalendarNumberSystem[S]] extends NumberSystem[S] { this: S =>
}

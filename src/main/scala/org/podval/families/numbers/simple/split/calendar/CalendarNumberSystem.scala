package org.podval.families.numbers.simple.split.calendar

import org.podval.families.numbers.simple.split.base.NumberSystem

trait CalendarNumberSystem[S <: CalendarNumberSystem[S]] extends NumberSystem[S] { this: S =>
}

package org.podval.families.calendar.monolithic

trait Calendar {

  trait CalendarMember {
    def calendar: Calendar
  }

  type Year <: YearBase

  abstract class YearBase(number: Int)
    extends Numbered[Year](number) with CalendarMember
  { this: Year =>
    final def isLeap: Boolean = Year.isLeap(number)

    final def next: Year = Year(number + 1)

    def lengthInDays: Int
  }

  abstract class YearCompanionBase {
    def apply(number: Int): Year

    final def apply(month: Month): Year = ???

    def isLeap(yearNumber: Int): Boolean
  }

  val Year: YearCompanionBase

  type Month <: MonthBase

  abstract class MonthBase(number: Int)
    extends Numbered[Month](number) with CalendarMember
  { this: Month =>
    final def year: Year = Year(this)
  }

  abstract class MonthCompanion {
    type Name

    def apply(number: Int): Month
  }

  val Month: MonthCompanion
}

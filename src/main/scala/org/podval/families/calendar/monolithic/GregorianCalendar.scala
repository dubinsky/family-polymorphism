package org.podval.families.calendar.monolithic

class Named(name: String) {
  final override def toString: String = name
}

object GregorianCalendar extends Calendar {

  trait GregorianCalendarMember extends CalendarMember {
    final override def calendar: GregorianCalendar.type = GregorianCalendar.this
  }

  class Year(number: Int) extends YearBase(number) with GregorianCalendarMember {
    override def lengthInDays: Int = ???
  }

  object Year extends YearCompanionBase {
    override def apply(number: Int): Year = new Year(number)

    override def isLeap(yearNumber: Int): Boolean = ???

    val monthsInYear = 12
  }

  class Month(number: Int) extends MonthBase(number) with GregorianCalendarMember

  object Month extends MonthCompanion {
    override def apply(number: Int): Month = new Month(number)

    sealed class Name(name: String) extends Named(name)
    case object January   extends Name("January")
    case object February  extends Name("February")
    case object March     extends Name("March")
  }

  def gregorianEpoch: Int = ???
}


object UseInstance {
  def main(args: Array[String]): Unit = {
    val year: GregorianCalendar.Year = GregorianCalendar.Year(2017)
    val epoch: Int = year.calendar.gregorianEpoch
  }
}

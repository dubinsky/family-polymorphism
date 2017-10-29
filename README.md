# Studies in Family Polymorphism

## Warning
This is a part of the strongly-typed narrative; if you
- view type systems as bondage-and-discipline;
- believe that unit tests can replace compile-time checks;
- think that types limit your creativity -

this is not for you :)

## Motivation 

TODO example from the photo storage systems: Google, filesystem.
  Unique features (autocollages...) and commonality.
  Generic code: list contents; synchronize.

## Alternatives

C's function pointer tables - not typed enough.
Multi-parameter typeclasses - not powerful enough.
Java's generics - unable to tie the knot (no self-types).
Scala generics - ?

## Monolithic

### Family

As an example, we'll take a fragment of the code from https://github.com/jewish-calendar/dates.

#### Family trait

Family trait `Calendar` contains types `Year` and `Month`.
TODO RECOMMENDATION (it can be an abstract class too, if parameters make sense).
They are abstract type members so that we can "tie the knot".

#### Bounding types

Those types are bound by abstract classes `YearBase` and `MonthBase` respectively.
TODO RECOMMENDATION (it can be a trait if parameters aren't needed or can be captured later, in the instance)..
Both of those classes extends class `Numbered`, which contains featured common to both:

```Scala
abstract class Numbered[T <: Numbered[T]](val number: Int) extends Ordered[T] {

  final override def equals(other: Any): Boolean = other match {
    case that: Numbered[_] => number == that.number
    case _ => false
  }

  final override def hashCode: Int = number

  final override def compare(that: T): Int = this.number - that.number

  override def toString: String = number.toString
}
```

#### Companion "objects"

TODO family instance is a companion object for all Tis;
  sometimes an individual one is needed...

TODO Since 'def' is not "stable", types from famiy members can't be accessed via family();
     even if it was a 'val', paths leading to the same type could be different,
     so to tie the knot, such types have to be 'globalized' in the family F itself (in general;
     you may get lucky in some specific cases);

TODO It is still not clear how to deal with the sealed enumeration types...
     Referencing/importing them from stand-alone objects probably will be ugly, but what are the
     alternatives?

#### Self-types

Each member type bound type is self-typed as the appropriate member type, so that calling methods
that take parameters of a member type with `this` as parameter value inside the
bound type doesn't cause compilation errors like this: 

> overloaded method value apply with alternatives:
  (month: Calendar.this.Month)Calendar.this.Year <and>
  (number: Int)Calendar.this.Year
 cannot be applied to (Calendar.this.MonthBase)
    final def year: Year = Year(this)

or this:

> type mismatch;
  found   : MonthBase.this.type (with underlying type Calendar.this.MonthBase)
  required: Calendar.this.Month

TODO RECOMMENDATION
Instead of figuring out where self-typing is unavoidable, it's better to just self-type all the
bounding types.
 

TODO Are self-types critical for the monolithic encoding? See paper...

#### Family instance reference

Each member type has `calendar` method that returns the instance of the family
this object belongs to; since there are more than one family member type,
`calendar` method is declared in the `CalendarMember` trait that each member type extends. 

```Scala
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
```

### Instance of the family

#### Instance object

Everything is packaged as an object, since we only need one instance.
  (For some reason, 'final' in front of 'override' is allowed within an object...)

All abstract type members are given their final values by
direct override with a class definition
(it is illegal to indicate this by placing 'override' keyword before `class Year`...)


#### Companion "objects"

Companion "object" `val`s are given their final values by direct override with an object
definition
(it is illegal to indicate this by placing `override` keyword before `object Year`)


#### Self-types

Self-types of the classes are inherited from the bounding types, so there is no need to
declare them again.

#### Family instance reference

Trait `GregorianCalendarMember` implements the `calendar` method; it is mixed into each of the
instance classes.

Implementation of the `calendar` method refines its return type to the type of the instance,
so that methods defined on the instance can be called on the return of the `calendar`
method (see `UseInstance`).  
 

```Scala
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

class Named(name: String) {
  final override def toString: String = name
}


object UseInstance {
  def main(args: Array[String]): Unit = {
    val year: GregorianCalendar.Year = GregorianCalendar.Year(2017)
    val epoch: Int = year.calendar.gregorianEpoch
  }
}
```


### F-Bound

Alternatively, `Family` can be abstracted over the type of the instance;
that type can then be specified as the return type of the `family` method:

```Scala
trait Family[F <: Family[F]] {

  trait FamilyMember {
    def family: F
  }

  type T1 <: T1Base

  trait T1Base extends FamilyMember { this: T1 =>
    def add(t2: T1): T1 = createT1
    def double: T1 = add(this)
  }

  def createT1: T1

  type T2 <: T2Base

  trait T2Base extends FamilyMember { this: T2 =>
  }

  def createT2: T2
}
```

Since Family now takes type parameter `F`, instance can no longer be defined directly
as an `object```: `object Instance extends Family[Instance.type]` causes compilation
error:

> illegal cyclic reference involving object Instance

We need to define a non-final class Instance and derive the Instance object from it
(to ensure that there is only one instance, a private constructor can be added to
this class):

```Scala
class Instance extends Family[Instance] {

  trait FamilyMemberInstance extends FamilyMember {
    final override def family: Instance = Instance.this
  }

  final override type T1 = T1Instance

  class T1Instance extends T1Base with FamilyMemberInstance {
  }

  final override def createT1: T1 = new T1

  class T2 extends T2Base with FamilyMemberInstance {
  }

  final override def createT2: T2 = new T2

  final def familyInstanceMethod: Int = ???
}

object Instance extends Instance

object UseInstance {
  def main(args: Array[String]): Unit = {
    val t1: Instance.T1 = Instance.createT1
    val x: Int = t1.family.familyInstanceMethod
  }
}
```

TODO Looks like self-typing of the Family and using F#Ti instead of Ti is needed only
  for the split encoding...


### Sibling family members

TODO use NumberSystem as an example.

TODO two encodings: "selfType" abstract type and type parameters.

TODO Use toString in the NumberSystem example?

TODO Encoding using type parameters allows things like `Ordered[N]`, while
encoding using abstract types doe not. 

## Split

TODO Split encoding inspired by
 https://stackoverflow.com/questions/1154571/scala-abstract-types-vs-generics

TODO description

Creation of instances of Ti is delegated to createTi methods
  (which in a real example would have some parameters).
TODO Object creation methods (createTi) need to be globalized in the family trait itself -
just like the types Ti.


```Scala
// TODO To avoid compilation errors, instead of T1, use F#Ti.
trait Family[F <: Family[F]] { // TODO when is this needed?: this: F =>

  type T1 <: T1Base[F]

  def createT1: T1

  type T2 <: T2Base[F]

  def createT2: T2
}


trait FamilyMember [F <: Family[F]] {
  def family: F
}


// TODO To avoid compilation errors, instead of type aliases, use F#Ti directly,
// not type T2 = F#T2
trait T1Base[F <: Family[F]] extends FamilyMember[F] { this: F#T1 =>
  def add(t2: F#T1): F#T1 = family.createT1
  def double: F#T1 = add(this)
}


trait T2Base[F <: Family[F]] extends FamilyMember[F] { this: F#T2 =>
}
```

It is no longer possible to override abstract type Ti with the class
derived from TiBase, since it is defined in a separate file :)


```Scala
class Instance extends Family[Instance] {

  trait FamilyMemberInstance extends FamilyMember[Instance] {
    final override def family: Instance = Instance.this
  }

  final override type T1 = T1Instance

  final override def createT1: T1 = new T1Instance with FamilyMemberInstance

  final override type T2 = T2Instance

  final override def createT2: T2 = new T2 with FamilyMemberInstance

  final def familyInstanceMethod: Int = ???
}


abstract class T1Instance extends T1Base[Instance] {
}


abstract class T2Instance extends T2Base[Instance] {
}


object Instance extends Instance


object UseInstance {
  def main(args: Array[String]): Unit = {
    val t1: Instance.T1 = Instance.createT1
    val x: Int = t1.family.familyInstanceMethod
  }
}
```


## Extending the family

Extending the family:
  problem similar to the one with aliases: Scala doesn't know that two abstract types
    are the same, even if one is assigned to the other... Or does it - with `final```? 
  no "has-a" possible; always "is-a"?


## Refinement

Use Calendar with NumberSystem example for intermediate refinement (MomentBase).

## Guidelines

TODO


## TODO

Is this a "cake"?

TODO
When I put MonthDescriptor inside Month companion, types do not match;
when it is outside, they do, although it references a type from the companion (Month.Name).
This seems to no longer be the case?!

If Year is done as class and an instance assigned to the overridden val, it works;
if it is done as an override object, I get compiler errors:
   overriding method character in class YearBase of type => org.podval.calendar.dates.Jewish.Year.Character;
   method character has incompatible type
      override def character: Year.Character = (isLeap, kind)
TODO is this still the case?      

Derived Calendars are objects, but unless I do things like val x = Jewish,
I used to get initialization errors!
Which now went away for some reason! Maybe, because I took MonthDescriptor out of the Month companion!
TODO Well, it didn't go away completely! What a mess!

Turn this into a Gist, split up and embed in blog posts :)

Incorporate slides from the presentation...


## Conclusions

Although it is more elegant to use `apply()` methods from the companion objects to define
`val`s on the family, it is easy to break the initialization process, since the companion
objects are themselves `val`s (or `object`s). Globalize creation methods instead, and call
them from the companion objects. Normal users will still have the convenience of using
companion objects' `apply()` methods.

For the same reason, use of `val`s from the companion objects to define `val`s on the family
or other companion objects can cause initialization issues;
solution is to globalize the underlying `val` - or make the dependent one a `def` or `lazy val`
(unless, of course, it itself is used in initialization :)


With monolithic encoding, overriding types with class definitions and `val`s with `object`s
allows concise style not available with the split encoding.

When a family contains an inner family, monolithic encoding is more forgiving and easier to
construct; with the split encoding, outer family has to extend the inner one instead.
(Arguably, this is a more natural modelling of the situation anyways...)
It seems to be impossible (unlike with) to "rename" a type from the inner family, though,
since even with `final override type Point = Moment` the compiler doesn't treat `Point` and
`Moment` types as the same (as we saw earlier with the type aliases in family members).

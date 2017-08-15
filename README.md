# Studies in Family Polymorphism

## Warning
This is a part of the strongly-typed narrative; if you
- view type systems as bondage-and-discipline;
- believe that unit tests can replace compile-time checks;
- think that types limit your creativity -

this is not for you :)

## Motivation 

## Alternatives

C's function pointer tables - not typed enough.
Multi-parameter typeclasses - not powerful enough.
Java's generics - unable to tie the knot (no self-types).
Scala generics - ?


## TODO

Are self-types critical for the monolithic encoding? See paper...

Split encoding inspired by
 https://stackoverflow.com/questions/1154571/scala-abstract-types-vs-generics

Object creation methods (createTi) need to be globalized in the family trait itself -
just like the types Ti (both for the monolithic and split encoding encoding).

"Companion" objects.
 
Since 'def' is not "stable", types from famiy members can't be accessed via family();
     even if it was a 'val', paths leading to the same type could be different,
     so to tie the knot, such types have to be 'globalized' in the family F itself (in general;
     you may get lucky in some specific cases);

It is still not clear how to deal with the sealed enumeration types...
     Referencing/importing them from stand-alone objects probably will be ugly, but what are the
     alternatives?

Extending the family:
  problem similar to the one with aliases: Scala doesn't know that two abstract types
    are the same, even if one is assigned to the other... Or does it - with `final```? 
  no "has-a" possible; always "is-a"?
  
Add toString to the NumberSystem example?

Is this a "cake"?

When I put MonthDescriptor inside Month companion, types do not match; when it is outside, they do, although it
    references a type from the companion (Month.Name).

In derived Calendars, many companion vals are overridden by objects, but "override object" is not legal Scala.

If Year is done as class and an instance assigned to the overridden val, it works;
if it is done as an override object, I get compiler errors:
   overriding method character in class YearBase of type => org.podval.calendar.dates.Jewish.Year.Character;
   method character has incompatible type
      override def character: Year.Character = (isLeap, kind)

Derived Calendars are objects, but unless I do things like val x = Jewish, I used to get initialization errors!
    Which now went away for some reason! Maybe, because I took MonthDescriptor out of the Month companion!
    Well, it didn't go away completely! What a mess!

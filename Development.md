## Monolithic

### Basic

A family containing types T1 and T2.

Abstract type members Ti are used to "tie the knot".

Those types are bound by the traits TiBase
  (which in a real example would have some methods).

Creation of instances of Ti is delegated to createTi methods
  (which in a real example would have some parameters).

Each TiBase trait needs to be self-typed as Ti, so that calling methods
that take parameters of types Ti with `this` as parameter value inside the
TiBase doesn't cause compilation error: 

> type mismatch;
> found   : T1Base.this.type (with underlying type Family.this.T1Base)
> required: Family.this.T1

Each member type has `family` method that returns the instance of the family
this object belongs to; since there are usually more than one family member type,
`family` method is declared in the `FamilyMember` trait that each TiBase extends. 

```Scala
trait Family {
  trait FamilyMember {
    def family: Family
  }

  type T1 <: T1Base

  trait T1Base extends FamilyMember { this: T1 =>
    def add(t1: T1): T1 = createT1
    def double: T1 = add(this) 
  }

  def createT1: T1

  type T2 <: T2Base

  trait T2Base extends FamilyMember { this: T2 =>
  }

  def createT2: T2
}
```

Instance of the family.

All abstract types are given their final values:
  T1Instance is a class derived from T1Base; type member T1 is overridden with T1Instance;
  for T2, type member is overridden directly with a class definition
    (it is illegal to indicate this by placing 'override' keyword before 'class T2'...)

Creation methods are overridden appropriately.

Everything is packaged as an object, since we only need one instance.
  (For some reason, 'final' in front of 'override' is allowed - although this is an object...)

Self-types of TiInstance classes are inherited from the TiBase traits, so there is no need to
declare them again.
 
Trait `FamilyMemberInstance` implements the `family` method; it is mixed into each of the
  TiInstance classes.

Implementation of the `family` method refines its return type to the type of the instance,
so that methods defined on the instance can be called on the result of the `family`
method (see `main()`).  

```Scala
object Instance extends Family {

  trait FamilyMemberInstance extends FamilyMember {
    final override def family: Instance.type = Instance.this
  }

  override type T1 = T1Instance

  class T1Instance extends T1Base with FamilyMemberInstance {
  }

  override def createT1: T1 = new T1

  class T2 extends T2Base with FamilyMemberInstance {
  }

  override def createT2: T2 = new T2

  def familyInstanceMethod: Int = ???
}

object UseInstance {
  def main(args: Array[String]): Unit = {
    val t1: Instance.T1 = Instance.createT1
    val x: Int = t1.family.familyInstanceMethod
  }
}
```

### Companion "objects"

TODO family instance is a companion object for all Tis;
  sometimes an individual one is needed...

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

TODO two encodings: "selfType" abstract type and type parameters.


## Split

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

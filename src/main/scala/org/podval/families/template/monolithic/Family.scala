package org.podval.families.template.monolithic

// TODO need to use F-bound-polymorphism and self-type
// for FamilyMember.family to be typed precisely.
trait Family[F <: Family[F]] { this: F =>

  trait FamilyMember {
    def family: F
  }

  type T1 <: T1Base

  // TODO can be protected in monolithic encoding - but not in the split one.
  def createT1: T1

  // TODO need to use self-type to access createTi methods with precise types.
  trait T1Base extends FamilyMember { this: T1 =>
  }

  type T2 <: T1Base

  def createT2: T2

  trait T2Base extends FamilyMember { this: T2 =>
    final def t1: F#T1 = family.createT1
  }


  type Tn <: T1Base

  def createTn: Tn

  trait TnBase extends FamilyMember { this: Tn =>
  }
}

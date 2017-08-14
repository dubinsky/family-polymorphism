package org.podval.families.template.split.family

trait Family[F <: Family[F]] { this: F =>
  // TODO object creation methods (createTi) need to be globalized in the family trait itself -
  // just like the types Ti (same as in the monolithic encoding).

  type T1 <: T1Base[F]

  def createT1: T1

  type T2 <: T2Base[F]

  def createT2: T2

  type Tn <: TnBase[F]

  def createTn: Tn

  // TODO To avoid compilation errors, instead of T1, use F#Ti.
}

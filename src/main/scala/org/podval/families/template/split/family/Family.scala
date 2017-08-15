package org.podval.families.template.split.family

trait Family[F <: Family[F]] { // TODO this: F =>

  type T1 <: T1Base[F]

  def createT1: T1

  type T2 <: T2Base[F]

  def createT2: T2

  // TODO To avoid compilation errors, instead of T1, use F#Ti.
}

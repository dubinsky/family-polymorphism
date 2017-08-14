package org.podval.families.template.split.family

trait T1Base[F <: Family[F]] extends FamilyMember[F] { this: F#T1 =>

  // TODO To avoid compilation errors, instead of type aliases, use F#Ti directly.
  // type T2 = F#T2
}

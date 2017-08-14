package org.podval.families.template.split.family

trait FamilyMember [F <: Family[F]] {
  def family: F
}

package org.podval.families.numbers.simple.split.base

trait NumberSystem[S <: NumberSystem[S]] { //this: S =>
  import NumberSystem.Raw

  type Number <: NumberBase[S]

  def createNumber(raw: Raw): Number
}


object NumberSystem {
  type Raw = (Boolean, List[Int])
}

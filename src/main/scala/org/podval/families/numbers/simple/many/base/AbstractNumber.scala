package org.podval.families.numbers.simple.many.base

import org.podval.families.numbers.simple.many.base.NumberSystem.Raw

abstract class AbstractNumber[S <: NumberSystem[S]](raw: Raw) extends NumberBase[S] { this: S#Number =>
  final override def negative: Boolean = raw._1
  final override def digits: List[Int] = raw._2
}

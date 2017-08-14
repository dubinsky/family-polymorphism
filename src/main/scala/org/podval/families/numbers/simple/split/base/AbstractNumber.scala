package org.podval.families.numbers.simple.split.base

import org.podval.families.numbers.simple.split.base.NumberSystem.Raw

abstract class AbstractNumber[S <: NumberSystem[S]](raw: Raw) extends NumberBase[S] { this: S#Number =>
  final override def negative: Boolean = raw._1
  final override def digits: List[Int] = raw._2
}

package org.podval.families.numbers.simple.split.base

trait NumberBase[S <: NumberSystem[S]] extends Ordered[S#Number] { this: S#Number =>
  import NumberSystem.Raw

  protected def numberSystem: S

  protected final def create(raw: Raw): S#Number =
    numberSystem.createNumber(raw)

  def negative: Boolean

  def digits: List[Int]

  final def digit(n: Int): Int =
    if (digits.length >= n) digits(n) else 0

  final def digit(n: Int, value: Int): S#Number =
    create(negative, digits.padTo(n + 1, 0).updated(n, value))

  final def +(that: S#Number): S#Number = create(plusMinus(operationNegation = false, that))

  final def -(that: S#Number): S#Number = create(plusMinus(operationNegation = true, that))

  final def *(n: Int): S#Number = create(negative, digits map (n * _))

  private[this] final def plusMinus(operationNegation: Boolean, that: S#Number): Raw =
    ???

  final def compare(that: S#Number): Int =
    ???

  // TODO unchecked because of the erasure; compare NumberSystems...
  final override def equals(other: Any): Boolean =
    if (!other.isInstanceOf[S#Number]) false else compare(other.asInstanceOf[S#Number]) == 0
}

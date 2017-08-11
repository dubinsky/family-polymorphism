package org.podval.families.numbers.simple.one

trait NumberSystem {
  type Raw = (Boolean, List[Int])

  protected type Number <: NumberBase

  protected def createNumber(raw: Raw): Number

  trait NumberBase extends Ordered[Number] {

    protected def numberSystem: NumberSystem

    protected def create(raw: Raw): Number
    // This can be final, but defining it as
    //   = numberSystem.createNumber(raw)
    // causes an error:
    //   type mismatch;
    //   found   : org.podval.families.numbers.simple.one.NumberSystem#Number
    //   required: NumberSystem.this.Number

    def negative: Boolean

    def digits: List[Int]

    final def digit(n: Int): Int =
      if (digits.length >= n) digits(n) else 0

    final def digit(n: Int, value: Int): Number =
      create(negative, digits.padTo(n + 1, 0).updated(n, value))

    final def +(that: Number): Number = create(plusMinus(operationNegation = false, that))

    final def -(that: Number): Number = create(plusMinus(operationNegation = true, that))

    final def *(n: Int): Number = create(negative, digits map (n * _))

    private[this] final def plusMinus[T <: Number](operationNegation: Boolean, that: Number): Raw =
      ???

    final def compare(that: Number): Int =
      ???

    // TODO unchecked because of the erasure
    final override def equals(other: Any): Boolean =
      if (!other.isInstanceOf[Number]) false else compare(other.asInstanceOf[Number]) == 0

  }


  abstract class AbstractNumber(raw: Raw) extends NumberBase {
    final override def negative: Boolean = raw._1
    final override def digits: List[Int] = raw._2
  }
}

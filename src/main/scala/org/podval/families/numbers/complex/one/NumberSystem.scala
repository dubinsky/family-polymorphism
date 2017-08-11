package org.podval.families.numbers.complex.one

trait NumberSystem {
  type Raw = (Boolean, List[Int])

  protected type Point <: PointBase

  protected def createPoint(raw: Raw): Point

  protected type Interval <: IntervalBase

  protected def createInterval(raw: Raw): Interval

  // TODO rename as NumberBase for consistency with the 'simple' one
  // - and comment that calling it 'Number' is possible here.
  trait Number {

    protected type SelfType <: Number

    protected def create(raw: Raw): SelfType

    final def numberSystem: NumberSystem = NumberSystem.this

    // Defining this as
    //   = numberSystem.createPoint(raw)
    // causes an error:
    //   type mismatch;
    //   found   : org.podval.families.numbers.complex.one.NumberSystem#Point
    //   required: NumberSystem.this.Point
    protected final def createPoint(raw: Raw): Point =
      NumberSystem.this.createPoint(raw)

    // This can be final, but defining it as
    //   = numberSystem.createInterval(raw)
    // causes an error:
    //   type mismatch;
    //   found   : org.podval.families.numbers.complex.one.NumberSystem#Interval
    //   required: NumberSystem.this.Interval
    protected final def createInterval(raw: Raw): Interval =
      NumberSystem.this.createInterval(raw)

    def negative: Boolean

    def digits: List[Int]

    final def digit(n: Int): Int =
      if (digits.length >= n) digits(n) else 0

    final def digit(n: Int, value: Int): SelfType =
      create(negative, digits.padTo(n + 1, 0).updated(n, value))

    protected final def plus[T <: Number](that: Number): Raw =
      plusMinus(operationNegation = false, that)

    protected final def minus[T <: Number](that: Number): Raw =
      plusMinus(operationNegation = true, that)

    private[this] final def plusMinus[T <: Number](operationNegation: Boolean, that: Number): Raw =
      ???

    protected final def compare_(that: Number): Int =
      ???

    protected final def equals_(that: Number): Boolean =
      compare_(that) == 0
  }


  trait PointBase extends Number with Ordered[Point] {

    protected override type SelfType = Point

    protected override def create(raw: Raw): Point =
      createPoint(raw)

    final def +(that: Interval): Point = createPoint(plus(that))

    final def -(that: Interval): Point = createPoint(minus(that))

    final def -(that: Point): Interval = createInterval(minus(that))

    final def compare(that: Point): Int =
      compare_(that)

    // TODO unchecked because of the erasure
    final override def equals(other: Any): Boolean =
      if (!other.isInstanceOf[Point]) false else equals_(other.asInstanceOf[Point])

  }


  trait IntervalBase extends Number with Ordered[Interval] {

    protected override type SelfType = Interval

    protected override def create(raw: Raw): Interval =
      createInterval(raw)

    final def +(that: Interval): Interval = createInterval(plus(that))

    final def -(that: Interval): Interval = createInterval(minus(that))

    final def *(n: Int): Interval = createInterval(negative, digits map (n * _))

    final def compare(that: Interval): Int = compare_(that)

    // TODO unchecked because of the erasure
    final override def equals(other: Any): Boolean =
      if (!other.isInstanceOf[Interval]) false else equals_(other.asInstanceOf[Interval])
  }


  abstract class AbstractNumber(raw: Raw) extends Number {
    final override def negative: Boolean = raw._1
    final override def digits: List[Int] = raw._2
  }
}

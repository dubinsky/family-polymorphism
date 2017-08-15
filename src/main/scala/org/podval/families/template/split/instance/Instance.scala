package org.podval.families.template.split.instance

import org.podval.families.template.split.family.{Family, FamilyMember}

class Instance extends Family[Instance] {

  trait FamilyMemberInstance extends FamilyMember[Instance] {
    final override def family: Instance = Instance.this
  }

  final override type T1 = T1Instance

  final override def createT1: T1 = new T1Instance with FamilyMemberInstance

  final override type T2 = T2Instance

  final override def createT2: T2 = new T2 with FamilyMemberInstance

  final def familyInstanceMethod: Int = ???
}

object Instance extends Instance


object UseInstance {
  def main(args: Array[String]): Unit = {
    val t1: Instance.T1 = Instance.createT1
    val x: Int = t1.family.familyInstanceMethod
  }
}

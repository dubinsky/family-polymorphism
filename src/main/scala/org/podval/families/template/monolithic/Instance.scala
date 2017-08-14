package org.podval.families.template.monolithic

object Instance extends Family[Instance.type] {

  trait FamilyMemberInstance extends FamilyMember {
    final override def family: Instance.type = Instance.this
  }

  override type T1 = T1Instance

  override def createT1: T1 = new T1Instance with FamilyMemberInstance

  trait T1Instance extends T1Base { this: T1 =>
  }

  final override type T2 = T2Instance

  override def createT2: T2 = new T2Instance with FamilyMemberInstance

  trait T2Instance extends T2Base { this: T2 =>
  }


  override type Tn = TnInstance

  final override def createTn: Tn = new TnInstance with FamilyMemberInstance

  trait TnInstance extends TnBase { this: Tn =>
  }
}

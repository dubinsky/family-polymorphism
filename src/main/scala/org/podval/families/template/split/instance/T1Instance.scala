package org.podval.families.template.split.instance

import org.podval.families.template.split.family.T1Base

abstract class T1Instance extends T1Base[Instance] {
  final override def add(t2: Instance#T1): Instance#T1 = ???
}

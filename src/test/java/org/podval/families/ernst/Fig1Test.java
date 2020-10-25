package org.podval.families.ernst;

import org.testng.annotations.Test;
import org.testng.Assert;

public class Fig1Test {

  private void build(Fig1.Node n, Fig1.Edge e, boolean b) {
    e.n1 = e.n2 = n;
    Assert.assertEquals(b, n.touches(e));
  }

  @Test public void correct() {
    build(new Fig1.Node(), new Fig1.Edge(), true);
  }

  @Test public void correctOnOff() {
    build(new Fig1.OnOffNode(), new Fig1.OnOffEdge(), false);
  }

  @Test(expectedExceptions = ClassCastException.class) public void incorrectFailsAtRunTime() {
    build(new Fig1.OnOffNode(), new Fig1.Edge(), true); // ClassCastException!
  }

  @Test public void incorrectDoesntEvenFailAtRunTime() {
    build(new Fig1.Node(), new Fig1.OnOffEdge(), true); // "works"
  }
}

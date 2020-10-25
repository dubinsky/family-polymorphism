package org.podval.families.ernst;

// Note: code here is equivalent to that in Ernst's "Fig. 1".
// Usage tests are in Fig1Test.
// TODO move everything into Fig1Test?
interface Fig1 {
  class Node {
    boolean touches(final Edge e) {
      return (this == e.n1) || (this == e.n2);
    }
  }

  class Edge {
    Node n1;
    Node n2;
  }

  class OnOffNode extends Node {
    boolean touches(final Edge e) {
      return ((OnOffEdge) e).enabled && super.touches(e);
    }
  }

  class OnOffEdge extends Edge {
    boolean enabled = false;
  }
}

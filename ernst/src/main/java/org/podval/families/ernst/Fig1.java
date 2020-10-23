package org.podval.families.ernst;

// TODO
// - clean up (ternary expression, public in an interface, final)
// - remove main - its equivalent is in Fig1Test
// - move everything into Fig1Test?
interface Fig1 {
  class Node {
    boolean touches(Edge e) { return (this == e.n1) || (this == e.n2); }
  }

  class Edge { Node n1, n2; }

  class OnOffNode extends Node {
    boolean touches(Edge e) {
      return ((OnOffEdge) e).enabled ? super.touches(e) : false;
    }
  }

  class OnOffEdge extends Edge {
    boolean enabled;
    OnOffEdge() { this.enabled = false; }
  }

  public class Main {
    static void build(Node n, Edge e, boolean b) {
      e.n1 = e.n2 = n;
      if (b == n.touches(e)) System.out.println("OK");
    }

    public static void main(String[] args) {
      build(new Node(), new Edge(), true);
      build(new OnOffNode(), new OnOffEdge(), false);
      build(new OnOffNode(), new Edge(), true); // ClassCastException!
      build(new Node(), new OnOffEdge(), true); // "works"
    }
  }
}

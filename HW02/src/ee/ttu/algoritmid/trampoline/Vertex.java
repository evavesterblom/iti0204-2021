package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.HashSet;

public class Vertex {

    private final HashSet<Vertex> edges;
    private final Trampoline.Type type;
    public Point coordinate;
    public int force;
    public int fine = 0;

    public Vertex(int x, int y, Trampoline.Type type, int force) {
        this.coordinate = new Point(x, y);
        this.edges = new HashSet<>();
        this.type = type;
        this.force = force;
        if (Trampoline.Type.WITH_FINE == this.type) this.fine = this.force;
    }

    public void addDirectedEdge(Vertex whereToConnect) {
        if (this == whereToConnect) return; //no loopy loops
        this.edges.add(whereToConnect);
    }

    public HashSet<Vertex> getConnectedVertexes() {
        return this.edges;
    }
}

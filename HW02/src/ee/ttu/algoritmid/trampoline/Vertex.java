package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    private Point coordinate;
    private HashSet<Vertex> edges;
    private Trampoline.Type type;
    private int force;

    public Vertex(int x, int y, Trampoline.Type type, int force){
        this.coordinate = new Point(x, y);
        this.edges = new HashSet<>();
        this.type = type;
        this.force = force;
    }

    public void addEdge (Vertex vertex /*to where connect*/){
        this.edges.add(vertex);
    }
}

package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<Point,Vertex> vertexMap = new HashMap<>();
    HashMap<Point, List<Vertex>> connections = new HashMap<>();

    private Point minPoint;
    private Point maxPoint;

    public void createGraph(Trampoline[][] map){

        if (map.length == 0) return;

        var rows = map.length;
        var columns = map[0].length;

        minPoint = new Point(0, 0);
        maxPoint = new Point (rows-1, columns-1);

        for (var row = 0; row < rows; ++row){
            for (var col = 0; col < columns; ++col){

                var trampoline = map[row][col];
                var newVertex = new Vertex(row, col, trampoline.getType(), trampoline.getJumpForce());
                vertexMap.put(new Point(row,col), newVertex);

                var jumpForce = trampoline.getJumpForce();
                if (jumpForce > 0) {
                    var newPointEast = new Point(row, col + jumpForce);
                    var newPointSouth = new Point(row + jumpForce, col);
                    addConnection(newVertex, newPointEast);
                    addConnection(newVertex, newPointSouth);
                }
                updateEdges(newVertex);
            }
        }
    }

    private void updateEdges(Vertex vertex) {
        if (connections.containsKey(vertex.coordinate)){
            var from= connections.get(vertex.coordinate);
            for (var f : from){
                f.addDirectedEdge(vertex);
            }
        }
    }

    public Vertex getVertexAtPoint(int x, int y){
        var point = new Point(x, y);
        return vertexMap.get(point);
    }

    public Vertex getStartVertex(){
        return vertexMap.get(minPoint);
    }

    public Vertex getEndVertex(){
        return vertexMap.get(maxPoint);
    }

    private void addConnection(Vertex from, Point destination){

        if (!connections.containsKey(destination)){
            connections.put(destination, new ArrayList<>());
        }
        connections.get(destination).add(from);
    }
}

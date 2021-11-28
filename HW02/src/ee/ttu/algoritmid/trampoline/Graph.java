package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.HashMap;

public class Graph {

    HashMap<Point,Vertex> vertexMap = new HashMap<>();
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
                vertexMap.put(new Point(row,col), new Vertex(row, col, map[row][col].getType(), map[row][col].getJumpForce()));
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
}

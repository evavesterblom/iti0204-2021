package ee.ttu.algoritmid.trampoline;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Graph {

    HashMap<Point,Vertex> mapCoordinateVertex = new HashMap<>(); //key-point value-vertex
    HashMap<Point, List<Vertex>> mapCoordinateConnectionsFrom = new HashMap<>(); //key-point value-connections from

    private Point minPoint;
    private Point maxPoint;

    public void createGraph(Trampoline[][] map){
        if (map.length == 0) return;
        var rows = map.length;
        var columns = map[0].length;

        List<Vertex> rowUnconnectedVertexes = new ArrayList<>();
        List<Vertex>[] colUnconnectedVertexes = new List[columns];
        for (int i = 0; i < columns; ++i){
            colUnconnectedVertexes[i] = new ArrayList<Vertex>();
        }


        minPoint = new Point(0, 0);
        maxPoint = new Point (rows-1, columns-1);

        for (var row = 0; row < rows; ++row){
            rowUnconnectedVertexes.clear();

            for (var col = 0; col < columns; ++col){

                var trampoline = map[row][col];
                var newVertex = new Vertex(row, col, trampoline.getType(), trampoline.getJumpForce());
                mapCoordinateVertex.put(new Point(row,col), newVertex);
                var jumpForce = trampoline.getJumpForce();

                if (trampoline.getType() == Trampoline.Type.NORMAL ||
                trampoline.getType() == Trampoline.Type.WITH_FINE){
                    rowUnconnectedVertexes.add(newVertex);
                    colUnconnectedVertexes[col].add(newVertex);
                    var jumps = new int[]{jumpForce-1, jumpForce, jumpForce+1};
                        for (var jump : jumps){
                            if (jump > 0) {
                                var newPointEast = new Point(row, col + jump);
                                var newPointSouth = new Point(row + jump, col);
                                addConnection(newVertex, newPointEast);
                                addConnection(newVertex, newPointSouth);
                            }
                        }
                }
                if (trampoline.getType() == Trampoline.Type.WALL){
                    //all elements must go col-1 in mapCoordinateConnectionsFrom
                    if (rowUnconnectedVertexes.size() > 0){
                        var previousPointInRow = new Point(row, col-1);

                        for (var r : rowUnconnectedVertexes.toArray(new Vertex[0])){
                            var searchKey = new Point(row, r.coordinate.y + r.force);
                            mapCoordinateConnectionsFrom.get(searchKey).remove(r);
                            addConnection(r, previousPointInRow);
                            rowUnconnectedVertexes.remove(r);
                        }
                        updateEdges(mapCoordinateVertex.get(previousPointInRow),rowUnconnectedVertexes, colUnconnectedVertexes[col]);
                    }

                    //all elements must go row-1 in mapCoordinateConnectionsFrom
                    if (colUnconnectedVertexes[col].size() > 0) {
                        var previousPointInCol = new Point(row - 1, col);

                        for (var c : colUnconnectedVertexes[col].toArray(new Vertex[0])) {
                            var searchKey = new Point(c.coordinate.x + c.force, col);
                            mapCoordinateConnectionsFrom.get(searchKey).remove(c);
                            addConnection(c, previousPointInCol);
                            rowUnconnectedVertexes.remove(c);
                        }
                        updateEdges(mapCoordinateVertex.get(previousPointInCol),rowUnconnectedVertexes, colUnconnectedVertexes[col]);
                    }
                }
                // when code goes here, all connection in mapCoordinateConnectionsFrom are correct and updated
                updateEdges(newVertex, rowUnconnectedVertexes, colUnconnectedVertexes[col]);
            }
        }
    }

    private void updateEdges(Vertex vertex, List<Vertex> rowUnconnectedVertexes, List<Vertex> colUnconnectedVertexes) {
        if (mapCoordinateConnectionsFrom.containsKey(vertex.coordinate)){
            var from= mapCoordinateConnectionsFrom.get(vertex.coordinate);
            for (var f : from){
                f.addDirectedEdge(vertex);
                rowUnconnectedVertexes.remove(vertex);
                colUnconnectedVertexes.remove(vertex);
            }
        }
    }

    public Vertex getVertexAtPoint(int x, int y){
        var point = new Point(x, y);
        return mapCoordinateVertex.get(point);
    }

    public Vertex getStartVertex(){
        return mapCoordinateVertex.get(minPoint);
    }

    public Vertex getEndVertex(){
        return mapCoordinateVertex.get(maxPoint);
    }

    private void addConnection(Vertex from, Point destination){

        if (!mapCoordinateConnectionsFrom.containsKey(destination)){
            mapCoordinateConnectionsFrom.put(destination, new ArrayList<>());
        }
        mapCoordinateConnectionsFrom.get(destination).add(from);
    }
}

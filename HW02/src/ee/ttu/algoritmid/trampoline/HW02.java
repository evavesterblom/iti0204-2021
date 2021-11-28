package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        Graph graph = new Graph();
        graph.createGraph(map);
        var start = graph.getStartVertex();
        var end = graph.getEndVertex();

        //graph search and get result list of vertexes > translate to jumps
        BFSDjikstra bfs = new BFSDjikstra();
        var searchResult = bfs.search(start, end);
        var reversedPath = bfs.reversePathMap(searchResult, start, end);
        var getJumps = pathToJumps(reversedPath, end);


        return new Result() {
            @Override
            public List<String> getJumps() {
                return getJumps;
            }

            @Override
            public int getTotalFine() {
                return 0;
            }
        };
    }


    private List<String> pathToJumps(List<Vertex> BFSreversedPath, Vertex end){

        var jumps = new ArrayList<String>();
        if (BFSreversedPath == null || end == null) return jumps;


        for (int i = 0; i < BFSreversedPath.size()-1; ++i){
            var current = BFSreversedPath.get(i).coordinate;
            var next = BFSreversedPath.get(i + 1).coordinate;

            String jump = null;
            if (current.x == next.x) jump = "E" + (next.y - current.y);
            if (current.y == next.y) jump = "S" + (next.x - current.x);

            if (jump != null) jumps.add(jump);

            if(next == end.coordinate) break;
        }
        return jumps;
    }
}
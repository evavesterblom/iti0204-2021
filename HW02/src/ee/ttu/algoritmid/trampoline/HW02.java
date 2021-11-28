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

        //graph search and get result list of vertexes
        BFSDjikstra bfs = new BFSDjikstra();
        var d = bfs.search(start, end);
        var result = bfs.reversePathMap(d, start, end);

        //jumps
        var getJumps = new ArrayList<String>();
        for (int i = 0; i < result.size()-1; ++i){
            var current = result.get(i).coordinate;
            var next = result.get(i + 1).coordinate;

            String jump = null;
            if (current.x == next.x) jump = "E" + (next.y - current.y);
            if (current.y == next.y) jump = "S" + (next.x - current.x);

            if (jump != null) getJumps.add(jump);

            if(next == end.coordinate) break;
        }


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
}
package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        // method 1
        // create graph from map; find all possible shortest routes; find the route with the lowest fine(s); get jumps
        /*
        Graph graph = new Graph();
        graph.createGraph(map);
        var start = graph.getStartVertex();
        var end = graph.getEndVertex();

        BFSDjikstra bfs = new BFSDjikstra();
        var shortestRoutes = bfs.searchAllShortestRoutes(start, end);
        var routeWithLowestFine = bfs.searchRouteLowestFine(end, start, shortestRoutes);
        var finalRoute = bfs.getRoute(routeWithLowestFine, start, end);
        var jumps = routeToJumps(finalRoute, end);

        return new Result() {
            @Override
            public List<String> getJumps() {
                return jumps;
            }

            @Override
            public int getTotalFine() {
                return getSumFines(finalRoute);
            }
        };
         */

        // method 2
        BFSDjikstra bfs = new BFSDjikstra();
        var res = bfs.straightSearch(map);
        return new Result() {
            @Override
            public List<String> getJumps() {
                return res.resultRoute;
            }

            @Override
            public int getTotalFine() {
                return res.totalFine;
            }
        };

    }

    private List<String> routeToJumps(List<Vertex> route, Vertex end) {
        var jumps = new ArrayList<String>();
        if (route == null || end == null) return jumps;

        for (int i = 0; i < route.size() - 1; ++i) {
            var current = route.get(i).coordinate;
            var next = route.get(i + 1).coordinate;

            String jump = null;
            if (current.x == next.x) jump = "E" + (next.y - current.y);
            if (current.y == next.y) jump = "S" + (next.x - current.x);

            if (jump != null) jumps.add(jump);

            if (next == end.coordinate) break;
        }
        return jumps;
    }

    private int getSumFines(List<Vertex> vertex) {
        if (vertex == null) return 0;
        int totalFine = 0;

        for (var v : vertex) {
            totalFine = totalFine + v.fine;
        }
        return totalFine;
    }
}
package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        /* Method 1 - graphs, using HashMaps
        Graph graph = new Graph();
        graph.createGraph(map);
        var start = graph.getStartVertex();
        var end = graph.getEndVertex();

        BFSGraphs bfs = new BFSGraphs();
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

        /* Method 2 - without graphs, using HashMaps
        BFSOptimized bfs = new BFSOptimized();
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
         */

        //Method 2 - without graphs, using matrices
        BFSMatrix bfs = new BFSMatrix();
        var res = bfs.straightSearchWithoutMaps(map);
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


        // Method 3
        /*DFS dfs = new DFS();
        var res = dfs.search(map);
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
         */
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
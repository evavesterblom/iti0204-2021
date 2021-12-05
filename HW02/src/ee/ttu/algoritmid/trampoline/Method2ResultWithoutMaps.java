package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Method2ResultWithoutMaps {
    public Integer totalFine;
    public List<String> resultRoute;

    Method2ResultWithoutMaps(Point[][] bestRouteMap, Integer totalFine, Point end, Point start){
        if (totalFine == null)
        {
            this.totalFine = 0;
        }
        else {
            this.totalFine = totalFine;
        }

        if (bestRouteMap == null || end == null || start == null) {
            this.resultRoute =  new ArrayList<String>();
        } else {
            this.resultRoute = convertRouteMapToStrings(bestRouteMap, end, start);
        }
    }

    private List<String> convertRouteMapToStrings(Point[][] bestRouteMap, Point goal, Point source) {
        var timerStart = System.currentTimeMillis();

        if (bestRouteMap == null) return null;

        var result = new LinkedList<Point>();
        var v = goal;
        //if (bestRouteMap.containsKey(goal) || source == goal) {
        if (bestRouteMap[goal.x][goal.y] != null || source == goal) {
            while (bestRouteMap[v.x][v.y] != null) {
                result.push(v);
                v = bestRouteMap[v.x][v.y];
            }
            result.push(source);
        }

        //System.out.println("convertRouteMapToStrings took " + (System.currentTimeMillis()-timerStart));
        return routeToJumps(result, goal);
    }

    private List<String> routeToJumps(List<Point> route, Point end) {
        var timerStart = System.currentTimeMillis();

        var jumps = new ArrayList<String>();
        if (route == null || end == null) return jumps;

        for (int i = 0; i < route.size() - 1; ++i) {
            var current = route.get(i);
            var next = route.get(i + 1);

            String jump = null;
            if (current.x == next.x) jump = "E" + (next.y - current.y);
            if (current.y == next.y) jump = "S" + (next.x - current.x);

            if (jump != null) jumps.add(jump);

            if (next == end) break;
        }
        System.out.println("routeToJumps took " + (System.currentTimeMillis()-timerStart));
        return jumps;
    }

}

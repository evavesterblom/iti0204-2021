package ee.ttu.algoritmid.trampoline;

import java.util.*;
import java.util.List;

public class BFSGraph {

    public HashMap<Vertex, List<Vertex>> searchAllShortestRoutes(Vertex start, Vertex goal) {
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var distanceMap = new HashMap<Vertex, Integer>();
        var routeMap = new HashMap<Vertex, List<Vertex>>();
        var distance = 0;
        var found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found) {
            for (var element : unvisitedQueue.toArray(new Vertex[0])) {
                unvisitedQueue.remove(element);

                if (element == goal) found = true;

                var connectedVertexes = element.getConnectedVertexes();

                for (var v : connectedVertexes) {
                    //if found shorter distance
                    if (!distanceMap.containsKey(v) || distance < distanceMap.get(v)) {
                        distanceMap.put(v, distance);
                        var list = new ArrayList<Vertex>();
                        list.add(element);
                        routeMap.put(v, list);
                        unvisitedQueue.add(v);
                    } else if (distance == distanceMap.get(v)) {
                        routeMap.get(v).add(element);
                    }

                    if (!distanceMap.containsKey(v)) {
                        unvisitedQueue.add(v);
                    }
                }
            }
            distance = distance + 1;
        }
        return routeMap;
    }

    public HashMap<Vertex, Vertex> searchRouteLowestFine(Vertex start, Vertex goal, HashMap<Vertex, List<Vertex>> routes) {
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var fineMap = new HashMap<Vertex, Integer>();
        var routeMap = new HashMap<Vertex, Vertex>();
        var found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found) {
            for (var element : unvisitedQueue.toArray(new Vertex[0])) {
                unvisitedQueue.remove(element);

                if (element == goal) found = true;

                var connectedVertexes = routes.get(element);
                if (connectedVertexes != null) {
                    for (var v : connectedVertexes) {

                        var fine = element.fine + v.fine;

                        if (!fineMap.containsKey(v) || fine < fineMap.get(v)) {
                            fineMap.put(v, fine);
                            routeMap.put(v, element);
                            unvisitedQueue.add(v);
                        }
                    }
                }
            }
        }
        return routeMap;
    }

    public List<Vertex> reverseRoute(HashMap<Vertex, Vertex> routeMap, Vertex source, Vertex goal) {
        if (routeMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = goal;
        if (routeMap.containsKey(goal) || source == goal) {
            while (routeMap.containsKey(v)) {
                result.push(v);
                v = routeMap.get(v);
            }
            result.push(source);
        }
        return result;
    }

    public List<Vertex> getRoute(HashMap<Vertex, Vertex> routeMap, Vertex source, Vertex goal) {
        if (routeMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = source;

        if (routeMap.containsKey(source) || source == goal) {
            while (routeMap.containsKey(v)) {
                result.add(v);
                v = routeMap.get(v);
            }
            result.add(goal);
        }
        return result;
    }

}


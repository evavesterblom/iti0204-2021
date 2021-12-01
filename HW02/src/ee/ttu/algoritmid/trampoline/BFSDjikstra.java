package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class BFSDjikstra {

    public HashMap<Vertex, List<Vertex>> searchAllShortestRoutes(Vertex start, Vertex goal){
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var distanceMap = new HashMap<Vertex, Integer>();
        var previousMap = new HashMap<Vertex, List<Vertex>>();
        var distance = 0;
        var found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found){
            for (var element : unvisitedQueue.toArray(new Vertex[0])) {
                unvisitedQueue.remove(element);

                if (element == goal) found = true;

                var connectedVertexes = element.getConnectedVertexes();
                unvisitedQueue.addAll(connectedVertexes);

                for (var v : connectedVertexes){

                    //if found shorter distance
                    if(!distanceMap.containsKey(v) || distance < distanceMap.get(v)){
                        distanceMap.put(v, distance);
                        var list = new ArrayList<Vertex>();
                        list.add(element);
                        previousMap.put(v, list);
                    }
                    else if (distance == distanceMap.get(v)) {
                        previousMap.get(v).add(element);
                    }
                }
            }
            distance = distance + 1;
        }
        return previousMap;
    }

    public HashMap<Vertex, Vertex> searchRouteLowestFine(Vertex start, Vertex goal, HashMap<Vertex, List<Vertex>> routes){
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var fineMap = new HashMap<Vertex, Integer>();
        var previousMap = new HashMap<Vertex, Vertex>();
        var found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found){
            for (var element : unvisitedQueue.toArray(new Vertex[0])) {
                unvisitedQueue.remove(element);

                if (element == goal) found = true;

                var connectedVertexes = routes.get(element);
                if (connectedVertexes != null) {
                    unvisitedQueue.addAll(connectedVertexes);

                    for (var v : connectedVertexes) {

                        var fine = element.fine + v.fine;

                        if (!fineMap.containsKey(v) || fine < fineMap.get(v)) {
                            fineMap.put(v, fine);
                            previousMap.put(v, element);
                        }
                    }
                }
            }
        }
        return previousMap;
    }

    public List<Vertex> reverseRoute(HashMap<Vertex, Vertex> previousMap, Vertex source, Vertex goal){

        if (previousMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = goal;
        if(previousMap.containsKey(goal) || source == goal){
            while(previousMap.containsKey(v)){
                result.push(v);
                v = previousMap.get(v);
            }
            result.push(source);
        }
        return result;
    }

    public List<Vertex> getRoute(HashMap<Vertex, Vertex> routeMap, Vertex source, Vertex goal){
        if (routeMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = source;
        if(routeMap.containsKey(source) || source == goal){
            while(routeMap.containsKey(v)){
                result.add(v);
                v = routeMap.get(v);
            }
            result.add(goal);
        }
        return result;
    }
}

package ee.ttu.algoritmid.trampoline;

import java.util.*;
import java.util.List;

public class BFSDjikstra {
    //Method 1
    public HashMap<Vertex, List<Vertex>> searchAllShortestRoutes(Vertex start, Vertex goal) {
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var distanceMap = new HashMap<Vertex, Integer>();
        var previousMap = new HashMap<Vertex, List<Vertex>>();
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
                        previousMap.put(v, list);
                        unvisitedQueue.add(v);
                    } else if (distance == distanceMap.get(v)) {
                        previousMap.get(v).add(element);
                    }

                    if (!distanceMap.containsKey(v)) {
                        unvisitedQueue.add(v);
                    }
                }
            }
            distance = distance + 1;
        }
        return previousMap;
    }

    public HashMap<Vertex, Vertex> searchRouteLowestFine(Vertex start, Vertex goal, HashMap<Vertex, List<Vertex>> routes) {
        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var fineMap = new HashMap<Vertex, Integer>();
        var previousMap = new HashMap<Vertex, Vertex>();
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
                            previousMap.put(v, element);
                            unvisitedQueue.add(v);
                        }
                    }
                }
            }
        }
        return previousMap;
    }

    public List<Vertex> reverseRoute(HashMap<Vertex, Vertex> previousMap, Vertex source, Vertex goal) {
        if (previousMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = goal;
        if (previousMap.containsKey(goal) || source == goal) {
            while (previousMap.containsKey(v)) {
                result.push(v);
                v = previousMap.get(v);
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

    //Method 2
    public Method2Result straightSearch(Trampoline[][] map){
        long timerStart = System.currentTimeMillis();

        if (map == null) return new Method2Result(null, null, null, null);

        var rows = map.length;
        if (rows == 0) return new Method2Result(null, null, null, null);;
        var columns = map[0].length;

        //var distanceMap = new HashMap<Point, Integer>();
        //var fineMap = new HashMap<Point, Integer>();
        //var previousMap = new HashMap<Point, Point>();

        var distanceMap = new HashMap<Point, Integer>();
        var fineMap = new HashMap<Point, Integer>();
        var previousMap = new HashMap<Point, Point>();

        var unvisitedQueue = new ArrayDeque<Point>();
        boolean found = false;
        var start = new Point(0, 0);
        var end = new Point(rows-1, columns-1);


        unvisitedQueue.add(new Point(0, 0));
        fineMap.put(start, getPointFine(start, map));
        distanceMap.put(start, 0);

        while(!unvisitedQueue.isEmpty() && !found){
            var element = unvisitedQueue.poll();
            if (element.equals(end)) found = true;

            //get landing points for element
            var jumpLocations = getLandingPoints(element, map);
            var fine = fineMap.get(element);

            for (var jumpLocation : jumpLocations){
                var newDistance = distanceMap.get(element) + 1;
                //shorter distance
                var newFine = fine + getPointFine(jumpLocation, map);
                if (!distanceMap.containsKey(jumpLocation) || newDistance < distanceMap.get(jumpLocation)) {
                    distanceMap.put(jumpLocation, newDistance);
                    previousMap.put(jumpLocation, element);
                    unvisitedQueue.add(jumpLocation);
                    fineMap.put(jumpLocation, newFine);
                } else if (newDistance == distanceMap.get(jumpLocation)) {
                    if(!fineMap.containsKey(jumpLocation) || newFine < fineMap.get(jumpLocation)){
                        previousMap.put(jumpLocation, element);
                        unvisitedQueue.add(jumpLocation);
                        fineMap.put(jumpLocation, newFine);
                    }
                }
            }
            //distance = distance + 1;
        }
        var totalFine = fineMap.get(end);
        System.out.println("straightSearch took " + (System.currentTimeMillis()-timerStart));
        var r = new Method2Result(previousMap, totalFine, end, start);
        return r;
    }

    //Method 3
    public Method2ResultWithoutMaps straightSearchWithoutMaps(Trampoline[][] map){
        long timerStart = System.currentTimeMillis();

        if (map == null) return new Method2ResultWithoutMaps(null, null, null, null);

        var rows = map.length;
        if (rows == 0) return new Method2ResultWithoutMaps(null, null, null, null);;
        var columns = map[0].length;

        //var distanceMap = new HashMap<Point, Integer>();
        //var fineMap = new HashMap<Point, Integer>();
        //var previousMap = new HashMap<Point, Point>();

        var distanceMap = new Integer[rows][columns]; //new HashMap<Point, Integer>();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                distanceMap[i][j] = -1;
            }
        }

        var fineMap =  new Integer[rows][columns]; //new HashMap<Point, Integer>();
        var previousMap = new Point[rows][columns]; //new HashMap<Point, Point>();

        var unvisitedQueue = new ArrayDeque<Point>();
        boolean found = false;
        var start = new Point(0, 0);
        var end = new Point(rows-1, columns-1);


        unvisitedQueue.add(new Point(0, 0));
        fineMap[0][0] =  getPointFine(start, map); //fineMap.put(start, getPointFine(start, map));
        distanceMap[0][0] = 0; //distanceMap.put(start, 0);

        while(!unvisitedQueue.isEmpty() && !found){
            var element = unvisitedQueue.poll();
            if (element.equals(end)) found = true;

            //get landing points for element
            var jumpLocations = getLandingPoints(element, map);
            var fine = fineMap[element.x][element.y]; //fineMap.get(element);

            for (var jumpLocation : jumpLocations){
                var newDistance = distanceMap[element.x][element.y] + 1;//distanceMap.get(element) + 1;
                //shorter distance
                var newFine = fine + getPointFine(jumpLocation, map);
                //if (!distanceMap.containsKey(jumpLocation) || newDistance < distanceMap.get(jumpLocation)) {
                 if(distanceMap[jumpLocation.x][jumpLocation.y] == -1 ||
                         newDistance < distanceMap[jumpLocation.x][jumpLocation.y]){
                     distanceMap[jumpLocation.x][jumpLocation.y] = newDistance; //distanceMap.put(jumpLocation, newDistance);
                     previousMap[jumpLocation.x][jumpLocation.y] = element; //previousMap.put(jumpLocation, element);
                     unvisitedQueue.add(jumpLocation);
                     fineMap[jumpLocation.x][jumpLocation.y] = newFine; //fineMap.put(jumpLocation, newFine);
                } //else if (newDistance == distanceMap.get(jumpLocation)) {
                    else if (newDistance == distanceMap[jumpLocation.x][jumpLocation.y]) {
                    //if(!fineMap.containsKey(jumpLocation) || newFine < fineMap.get(jumpLocation)){
                     if(fineMap[jumpLocation.x][jumpLocation.y] == -1|| newFine < fineMap[jumpLocation.x][jumpLocation.y]){
                         previousMap[jumpLocation.x][jumpLocation.y] = element; //previousMap.put(jumpLocation, element);
                         unvisitedQueue.add(jumpLocation);
                         fineMap[jumpLocation.x][jumpLocation.y] = newFine; //fineMap.put(jumpLocation, newFine);
                    }
                }
            }
        }
        var totalFine = fineMap[end.x][end.y]; //fineMap.get(end);
        System.out.println("straightSearch took " + (System.currentTimeMillis()-timerStart));
        var r = new Method2ResultWithoutMaps(previousMap, totalFine, end, start);
        return r;
    }

    private List<Point> getLandingPoints(Point element, Trampoline[][] map) {
        var jumpingPoints = new ArrayList<Point>();

        //jump right
        // do +-1 check for point out of bounds + walls
        // 2. check if jumped out of bounds
        // 3. if wall, then jump to wall-1
        var trampoline =  map[element.x][element.y];
        var force = trampoline.getJumpForce();
        var wallEast = checkWallsBetweenJump(element, new Point(element.x, element.y+ force + 1), map);
        var wallSouth = checkWallsBetweenJump(element, new Point(element.x + force + 1, element.y), map);
        var jumps = new int[]{force - 1, force, force + 1};

        if (force == 0) jumps = new int[]{force + 1};
        if (force == 1) jumps = new int[]{force, force + 1};

        //jump right
        for (var jump : jumps){
            var point = new Point(element.x, element.y + jump);
            var outOfBounds = checkIfOutOfBounds(point, map);
            if(outOfBounds){
                if (wallEast != null) {
                    point.y = wallEast.y-1; // falls to previous point
                    jumpingPoints.add(point);
                }
                break;
            }
            if (wallEast != null && wallEast.y <= point.y){
                point.y = wallEast.y-1; // falls to previous point
                jumpingPoints.add(point);
                break;
            }
            if(!point.equals(element)) jumpingPoints.add(point);
        }

        //jump down
        for (var jump : jumps){
            var point = new Point(element.x + jump, element.y);
            var outOfBounds = checkIfOutOfBounds(point, map);
            if(outOfBounds){
                if (wallSouth != null) {
                    point.x = wallSouth.x-1; // falls to previous point
                    jumpingPoints.add(point);
                }
                break;
            }
            if (wallSouth != null && wallSouth.x <= point.x){
                point.x = wallSouth.x-1; // falls to previous point
                jumpingPoints.add(point);
                break;
            }
            if(!point.equals(element)) jumpingPoints.add(point);
        }
        return jumpingPoints;
    }

    //return first point jump found
    private Point checkWallsBetweenJump(Point from, Point to, Trampoline[][] map){
        //jumping right  x=x
        var toJump = new Point(to.x, to.y);
        var rows = map.length;
        var columns = map[0].length;

        // if out of bounds
        if (toJump.x > rows - 1) toJump.x = rows - 1;
        if (toJump.y > columns - 1) toJump.y = columns - 1;

        if (from.x == toJump.x){
            for (var y = from.y; y <= toJump.y; ++y){
                if (map[from.x][y].getType() == Trampoline.Type.WALL) return new Point(from.x, y);
            }
        }

        //jumping left y=y
        if (from.y == toJump.y){
            for (var x = from.x; x <= toJump.x; ++x){
                if (map[x][from.y].getType() == Trampoline.Type.WALL) return new Point(x, from.y);
            }
        }

        return null;
    }

    private boolean checkIfOutOfBounds(Point point, Trampoline[][] map){
        var rows = map.length;
        var columns = map[0].length;
        if (point.x > rows - 1) return true;
        if (point.y > columns - 1) return true;
        if (point.x < 0) return true;
        if (point.y < 0) return true;
        return false;
    }

    private Integer getPointFine(Point point, Trampoline[][] map){
        if (point == null || map == null) return 0;

        if(map[point.x][point.y].getType() == Trampoline.Type.WITH_FINE) return map[point.x][point.y].getJumpForce();

        return 0;
    }



}


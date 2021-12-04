package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class BFSMatrix {
    List<Integer>[] rowWalls;
    List<Integer>[] columnWalls;
    boolean plusMinus = true;
    int[][] distanceMap;
    int[][] fineMap;
    Point[][] previousMap;
    Trampoline[][] trampolineMap;

    //Method 2 (search uses HashMaps)
    public Method2Result straightSearch(Trampoline[][] map) {
        if (map == null) return new Method2Result(null, null, null, null);

        var rows = map.length;
        if (rows == 0) return new Method2Result(null, null, null, null);
        var columns = map[0].length;

        var distanceMap = new HashMap<Point, Integer>();
        var fineMap = new HashMap<Point, Integer>();
        var previousMap = new HashMap<Point, Point>();

        var unvisitedQueue = new ArrayDeque<Point>();
        boolean found = false;
        var start = new Point(0, 0);
        var end = new Point(rows - 1, columns - 1);


        unvisitedQueue.add(new Point(0, 0));
        fineMap.put(start, getTrampolineFine(start));
        distanceMap.put(start, 0);

        while (!unvisitedQueue.isEmpty() && !found) {
            var element = unvisitedQueue.poll();
            if (element.equals(end)) found = true;

            //get landing points for element
            var jumpLocations = getLandingPoints(element, map);
            var fine = fineMap.get(element);

            for (var jumpLocation : jumpLocations) {
                var newDistance = distanceMap.get(element) + 1;
                //shorter distance
                var newFine = fine + getTrampolineFine(jumpLocation);
                if (!distanceMap.containsKey(jumpLocation) || newDistance < distanceMap.get(jumpLocation)) {
                    distanceMap.put(jumpLocation, newDistance);
                    previousMap.put(jumpLocation, element);
                    unvisitedQueue.add(jumpLocation);
                    fineMap.put(jumpLocation, newFine);
                } else if (newDistance == distanceMap.get(jumpLocation)) {
                    if (!fineMap.containsKey(jumpLocation) || newFine < fineMap.get(jumpLocation)) {
                        previousMap.put(jumpLocation, element);
                        unvisitedQueue.add(jumpLocation);
                        fineMap.put(jumpLocation, newFine);
                    }
                }
            }
        }
        var totalFine = fineMap.get(end);
        var r = new Method2Result(previousMap, totalFine, end, start);
        return r;
    }

    //Method 2 optimized (uses plain matrices)
    public Method2ResultWithoutMaps straightSearchWithoutMaps(Trampoline[][] map) {
        this.trampolineMap = map;
        if (map == null) return new Method2ResultWithoutMaps(null, null, null, null);
        var rows = map.length;
        if (rows == 0) return new Method2ResultWithoutMaps(null, null, null, null);
        var columns = map[0].length;

        rowWalls = new ArrayList[rows];
        columnWalls = new ArrayList[columns];

        distanceMap = new int[rows][columns];
        fineMap = new int[rows][columns];
        previousMap = new Point[rows][columns];
        fillMap(rows, columns, distanceMap, -1);

        boolean found = false;
        var start = new Point(0, 0);
        var end = new Point(rows - 1, columns - 1);
        updateDistance(start, 0);
        updateFine(start, getTrampolineFine(start));
        var unvisitedQueue = new LinkedList<Point>();

        unvisitedQueue.add(start);
        while (!unvisitedQueue.isEmpty() && !found) {
            var point = unvisitedQueue.poll();
            if (point.equals(end)) found = true;

            var children = getLandingPoints(point, map); //get landing points for element
            var fine = getAccumulatedFine(point);
            var newChildDistance = getAccumulatedDistance(point) + 1;

            for (var child : children) {
                var currentChildDistance = getAccumulatedDistance(child);
                var newChildFine = fine + getTrampolineFine(child);
                var currentChildFine = getAccumulatedFine(child);

                if (notVisited(child) || newChildDistance < currentChildDistance) { //shorter
                    updateDistance(child, newChildDistance);
                    updateRoute(child, point);
                    updateFine(child, newChildFine);
                    unvisitedQueue.add(child);

                } else if (newChildFine < currentChildFine) { //same distance but better
                    updateRoute(child, point);
                    updateFine(child, newChildFine);
                    unvisitedQueue.add(child);
                }
            }
        }
        var totalFine = getAccumulatedFine(end);
        return new Method2ResultWithoutMaps(previousMap, totalFine, end, start);
    }

    private void updateFine(Point point, int fine) {
        fineMap[point.x][point.y] = fine;
    }

    private void updateRoute(Point child, Point parent) {
        previousMap[child.x][child.y] = parent;
    }

    private void updateDistance(Point point, int distance) {
        distanceMap[point.x][point.y] = distance;
    }

    private boolean notVisited(Point point) {
        return getAccumulatedDistance(point) == -1;
    }

    private Integer getAccumulatedDistance(Point point) {
        if (point == null) return 0;
        return distanceMap[point.x][point.y];
    }

    private Integer getAccumulatedFine(Point point) {
        if (point == null) return 0;
        return fineMap[point.x][point.y];
    }

    private void fillMap(int rows, int columns, int[][] distanceMap, int fillValue) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                distanceMap[i][j] = fillValue;
            }
        }
    }

    private List<Point> getLandingPoints(Point element, Trampoline[][] map) {
        var jumpingPoints = new ArrayList<Point>();

        // if rowWalls[x] is null, the walls are not yet scanned, scan whole row x
        if (rowWalls[element.x] == null) {
            var listRow = new ArrayList<Integer>();
            rowWalls[element.x] = listRow;
            for (var y = 0; y < map[0].length; ++y) {
                if (map[element.x][y].getType() == Trampoline.Type.WALL) listRow.add(y);
            }
        }
        // if rowColumns[y] is null, the walls are not yet scanned, scan whole column y
        if (columnWalls[element.y] == null) {
            var listColumn = new ArrayList<Integer>();
            columnWalls[element.y] = listColumn;
            for (var x = 0; x < map.length; ++x) {
                if (map[x][element.y].getType() == Trampoline.Type.WALL) listColumn.add(x);
            }
        }

        var force = map[element.x][element.y].getJumpForce();

        var wallEast = checkWallsBetweenJump(element, new Point(element.x, element.y + force + 1), map);
        var wallSouth = checkWallsBetweenJump(element, new Point(element.x + force + 1, element.y), map);

        var jumps = new int[]{force - 1, force, force + 1};
        if (force == 0) jumps = new int[]{force + 1};
        if (force == 1) jumps = new int[]{force, force + 1};
        if(!plusMinus) jumps = new int[]{force};

        for (var jump : jumps) {
            var point = new Point(element.x, element.y + jump); //jump right
            var outOfBounds = checkIfOutOfBounds(point, map);
            if (outOfBounds) {
                if (wallEast != null) {
                    point.y = wallEast.y - 1; // falls to previous point
                    jumpingPoints.add(point);
                }
                break;
            }
            if (wallEast != null && wallEast.y <= point.y) {
                point.y = wallEast.y - 1; // falls to previous point
                jumpingPoints.add(point);
                break;
            }
            if (!point.equals(element)) jumpingPoints.add(point);
        }

        for (var jump : jumps) {
            var point = new Point(element.x + jump, element.y); //jump down
            var outOfBounds = checkIfOutOfBounds(point, map);
            if (outOfBounds) {
                if (wallSouth != null) {
                    point.x = wallSouth.x - 1; // falls to previous point
                    jumpingPoints.add(point);
                }
                break;
            }
            if (wallSouth != null && wallSouth.x <= point.x) {
                point.x = wallSouth.x - 1; // falls to previous point
                jumpingPoints.add(point);
                break;
            }
            if (!point.equals(element)) jumpingPoints.add(point);
        }
        return jumpingPoints;
    }

    private Point checkWallsBetweenJump(Point from, Point to, Trampoline[][] map) { //return first wall point
        var toJump = new Point(to.x, to.y);
        var rows = map.length;
        var columns = map[0].length;

        // if out of bounds
        if (toJump.x > rows - 1) toJump.x = rows - 1;
        if (toJump.y > columns - 1) toJump.y = columns - 1;

        if (from.x == toJump.x) { //jumping right  x=x
            var rowList = rowWalls[from.x];
            if (!(rowList == null || rowList.isEmpty())) {
                //check if wall between from.y - toJump.y
                var indexBegin = Collections.binarySearch(rowList, from.y);
                var indexEnd = Collections.binarySearch(rowList, toJump.y);
                if (indexBegin != indexEnd) return new Point(from.x, rowList.get(indexBegin + 1));
            }
        }

        if (from.y == toJump.y) { //jumping left y=y
            var columnList = columnWalls[from.y];
            if (!(columnList == null || columnList.isEmpty())) {
                //check if wall between - begin from.x; end toJump.x
                var indexBegin = Collections.binarySearch(columnList, from.x);
                var indexEnd = Collections.binarySearch(columnList, toJump.x);
                if (indexBegin != indexEnd) return new Point(columnList.get(indexBegin + 1), from.y);
            }
        }
        return null;
    }

    private boolean checkIfOutOfBounds(Point point, Trampoline[][] map) {
        var rows = map.length;
        var columns = map[0].length;
        if (point.x > rows - 1) return true;
        if (point.y > columns - 1) return true;
        if (point.x < 0) return true;
        return point.y < 0;
    }

    private Integer getTrampolineFine(Point point) {
        if (point == null || trampolineMap == null) return 0;
        if (trampolineMap[point.x][point.y].getType() == Trampoline.Type.WITH_FINE) return trampolineMap[point.x][point.y].getJumpForce();
        return 0;
    }

}

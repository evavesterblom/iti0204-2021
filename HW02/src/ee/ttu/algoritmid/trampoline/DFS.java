package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class DFS {
    List<Integer>[] rowWalls;
    List<Integer>[] columnWalls;
    boolean plusMinus = true;

    public Method2ResultWithoutMaps search(Trampoline[][] map){
        if (map == null) return new Method2ResultWithoutMaps(null, null, null, null);

        var rows = map.length;
        if (rows == 0) return new Method2ResultWithoutMaps(null, null, null, null);
        var columns = map[0].length;


        rowWalls = new ArrayList[rows];
        columnWalls = new ArrayList[columns];
        var start = new Point(0, 0);
        var end = new Point(rows - 1, columns - 1);
        var stack = new Stack<PointPair>();
        var bestDistance = Integer.MAX_VALUE;
        var bestFine = Integer.MAX_VALUE;

        var distanceMap = new Integer[rows][columns];
        var fineMap = new Integer[rows][columns];
        var previousMap = new Point[rows][columns];

        fineMap[0][0] = getTrampolineFine(start, map);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                distanceMap[i][j] = -1;
            }
        }

        //while
        stack.push(new PointPair(start, null));
        while(!stack.isEmpty()){
            var pair = stack.pop();
            var point = pair.point;
            var parent = pair.parent;
            var newElementDistance = getDistance(parent, distanceMap) + 1;
            var newFine = getFine(parent, fineMap) + getTrampolineFine(point, map);

            if (point == end){
                bestDistance = newElementDistance;
                bestFine = newFine;
            }

            //unvisited
            if(distanceMap[point.x][point.y] == -1){
                updateDistance(point, newElementDistance, distanceMap);

                var children = getLandingPoints(point, map);
                var childDistance = newElementDistance + 1;

                for (var child : children){
                    var newChildFine = newFine + getTrampolineFine(child, map);

                    if(notVisited(child, distanceMap)) {
                        stack.push(new PointPair(child, point));
                        updateParent(child, point, previousMap);
                        updateFine(child, newChildFine, fineMap);
                    }
                    else if(childDistance < getDistance(child, distanceMap)){
                        updateParent(child, point, previousMap);
                        updateDistance(child, childDistance , distanceMap);
                    }
                    else if(childDistance == getDistance(child, distanceMap)){
                        if (newChildFine < getFine(child, fineMap)){
                            updateParent(child, point, previousMap);
                            updateFine(child, newChildFine, fineMap);
                        }
                    }
                }
            }
            else if (newElementDistance < getDistance(point, distanceMap)){
                updateDistance(point, newElementDistance, distanceMap);
                updateParent(point, parent, previousMap);
                updateFine(point, newFine, fineMap);
                // update children again?
            }
            else if (newElementDistance == getDistance(point, distanceMap)){
                if(newFine < getFine(point, fineMap)){
                    updateFine(point, newFine, fineMap);
                    updateParent(point, parent, previousMap);
                }
            }
        }
        return new Method2ResultWithoutMaps(previousMap, getFine(end, fineMap), end, start);
    }

    private void updateFine(Point point, int fine, Integer[][] fineMap) {
        fineMap[point.x][point.y] = fine;
    }

    private void updateDistance(Point point, int distance, Integer[][] distanceMap) {
        distanceMap[point.x][point.y] = distance;
    }

    private int getFine(Point point, Integer[][] fineMap) {
        if (point == null) return 0;
        return fineMap[point.x][point.y];
    }

    private void updateParent(Point jumpLocation, Point element, Point[][] previousMap) {
        previousMap[jumpLocation.x][jumpLocation.y] = element;
    }

    private boolean notVisited(Point jumpLocation, Integer[][] distanceMap) {
        return distanceMap[jumpLocation.x][jumpLocation.y] == -1;
    }

    private int getDistance(Point point, Integer[][] distanceMap) {
        if (point == null) return 0;
        return distanceMap[point.x][point.y];
    }

    private Integer getTrampolineFine(Point point, Trampoline[][] map) {
        if (point == null || map == null) return 0;
        if (map[point.x][point.y].getType() == Trampoline.Type.WITH_FINE) return map[point.x][point.y].getJumpForce();
        return 0;
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

    private class PointPair {
        public Point point;
        public Point parent;

        public PointPair(Point point, Point parent){
            this.point = point;
            this.parent = parent;
        }
    }

}



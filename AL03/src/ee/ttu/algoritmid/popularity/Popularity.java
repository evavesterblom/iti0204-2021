package ee.ttu.algoritmid.popularity;

import java.awt.*;
import java.util.HashMap;

public class Popularity {

    private int numberOfPictureInMostPopularLocation;
    private HashMap<Point, Integer> pointPopularityMap;

    public Popularity(int maxCoordinates) {
        pointPopularityMap = new HashMap<>();
    }

    /**
     * @param x, y - coordinates
     * adds a picture at the point with coordinates (x, y)
     */
    void addPoint(Integer x, Integer y) {
        Point point = new Point(x, y);
        Integer popularity = 1;
        if (pointPopularityMap.containsKey(point)){
            popularity = pointPopularityMap.get(point) + 1;
            pointPopularityMap.replace(point, popularity);
        }
        else {
            pointPopularityMap.put(point, popularity);
        }
        if (numberOfPictureInMostPopularLocation < popularity) numberOfPictureInMostPopularLocation = popularity;
    }

    /**
     * @param x, y - coordinates
     * @return the number of occurrennces of the point
     */
    int pointPopularity(Integer x,Integer y) {
        Point point = new Point(x, y);
        if (!pointPopularityMap.containsKey(point)) return 0;
        return pointPopularityMap.get(point);
    }


    /**
     * @return the number of occurrennces of the most popular point
     */
    int maxPopularity() {
        return numberOfPictureInMostPopularLocation;
    }

}
package ee.ttu.algoritmid.flights;

import java.util.AbstractMap;
import java.util.Set;
import java.util.TreeMap;

public class Runner {
    public static void main(String[] args) throws Exception {

        TreeMap<Double, String> map = new TreeMap<>();
        map.put(1.1341, "Toomas");
        map.put(4.1666, "Mai");
        map.put(6.1666, "Kai");
        map.put(8.10, "Olga");
        map.put(11.18, "Dmitri");
        map.put(14.76, "Volodja");
        Set<Double> testSet = map.tailMap(8.0).keySet();
        var simpleSet = map.tailMap(8.0).entrySet();
        var a = map.tailMap(8.0).headMap(12.0);
        var subMapTestInclusive = map.subMap(2.0, true, 7.0, true);
    }
}

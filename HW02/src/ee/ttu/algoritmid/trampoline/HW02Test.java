package ee.ttu.algoritmid.trampoline;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HW02Test {

    HW02 solution;

    @Before
    public void setUp() {
        solution = new HW02();
    }

    @Test
    public void testEmptyMap() {
        var result = solution.play(new Trampoline[][]{});
        assertTrue(result.getJumps().isEmpty());
        assertEquals(result.getTotalFine(), 0);
    }

    @Test
    public void testOneElement() {
        String[][] forceMap = {
                {"0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertTrue(result.getJumps().isEmpty());
        assertEquals(result.getTotalFine(), 0);
    }

    @Test
    public void testTwoElements() {
        String[][] forceMap = {
                {"1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E1"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testManyElements() {
        String[][] forceMap = {
                {"1", "2", "2"},
                {"2", "10", "1"},
                {"3", "1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var actualResult = solution.play(trampoline);
        var acceptableResultList = new ArrayList<List<String>>();
        acceptableResultList.add(List.of("S1", "E2", "S1"));
        acceptableResultList.add(List.of("E2", "S2"));
        acceptableResultList.add(List.of("S2", "E2"));
        acceptableResultList.add(List.of("E1", "S2", "E1"));

        assertTrue(isAcceptableResult(actualResult, acceptableResultList));
        assertEquals(0, actualResult.getTotalFine());
    }

    @Test
    public void testFines() {
        String[][] forceMap = {
                {"1", "f1"},
                {"1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("S1", "E1"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testFines_WhenMultipleFines_Small() {
        String[][] forceMap = {
                {"f1", "f1"},
                {"1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("S1", "E1"), result.getJumps());
        assertEquals(1, result.getTotalFine());
    }

    @Test
    public void testFines_WhenMultipleFines_PlusMinus() {
        String[][] forceMap = {
                {"1", "0"},
                {"f1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E1", "S1"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testWall2x3() {
        String[][] forceMap = {
                {"33", "1", "w0"},
                {"11", "1", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E1", "S1", "E1"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testWall3x4() {
        String[][] forceMap = {
                {"10", "2", "w0", "2"},
                {"3", "1", "4", "1"},
                {"w0", "2", "2", "0"}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var actualResult = solution.play(trampoline);
        var acceptableResultList = new ArrayList<List<String>>();
        acceptableResultList.add(List.of("S1", "E3", "S1"));
        acceptableResultList.add(List.of("E1", "S2", "E2"));

        assertTrue(isAcceptableResult(actualResult, acceptableResultList));
        assertEquals(0, actualResult.getTotalFine());
    }

    @Test
    public void testPerformance() {
        var trampoline = forceMapToTrampoline(createMap(2000, 2000));
        var actualResult = solution.play(trampoline);

        assertTrue(actualResult.getJumps().size() > 0);
        assertEquals(0, actualResult.getTotalFine());
    }

    private Trampoline[][] forceMapToTrampoline(String[][] forceMap) {
        Trampoline[][] map = new Trampoline[forceMap.length][forceMap[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int finalI = i;
                int finalJ = j;

                map[i][j] = new Trampoline() {
                    @Override
                    public int getJumpForce() {
                        return getElementJumpForce(forceMap[finalI][finalJ]);
                    }

                    @Override
                    public Type getType() {
                        return getElementType(forceMap[finalI][finalJ]);
                    }
                };
            }
        }
        return map;
    }

    private Integer getElementJumpForce(String element) {
        var token = element.charAt(0);

        if (Character.isAlphabetic(token)) {
            return Integer.parseInt(element.substring(1));
        }
        return Integer.parseInt(element);
    }

    private Trampoline.Type getElementType(String element) {
        String elementString = element;

        if (elementString.contains("f")) return Trampoline.Type.WITH_FINE;

        if (elementString.contains("w")) return Trampoline.Type.WALL;

        return Trampoline.Type.NORMAL;
    }

    private boolean isAcceptableResult(Result result, ArrayList<List<String>> possibleResults) {
        var resultList = result.getJumps();

        for (var r : possibleResults) {
            if (r.equals(resultList)) return true;
        }
        return false;
    }

    private String[][] createMap(int rows, int cols) {
        String[][] map = new String[rows][cols];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j] = "1";
            }
        }
        map[rows - 1][cols - 1] = "0";
        return map;
    }
}
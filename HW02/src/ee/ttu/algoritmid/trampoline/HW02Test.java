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
    public void setUp(){
        solution = new HW02();
    }

    @Test
    public void testEmptyMap(){
        var result = solution.play(new Trampoline[][]{});
        assertTrue(result.getJumps().isEmpty());
        assertEquals(result.getTotalFine(), 0);
    }

    @Test
    public void testOneElement(){
        int[][] forceMap = {
                {0}
        };
        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);
        assertTrue(result.getJumps().isEmpty());
        assertEquals(result.getTotalFine(), 0);
    }

    @Test
    public void testTwoElements(){
        int[][] forceMap = {
                {1,0}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E1"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testManyElements(){
        int[][] forceMap = {
                {1,2,2},
                {2,10,1},
                {3,2,0}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E2", "S2"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    @Test
    public void testFines(){
        int[][] forceMap = {
                {1,1},
                {1,0}
        };

        var trampoline = forceMapToTrampoline(forceMap);
        var result = solution.play(trampoline);

        assertEquals(List.of("E2", "S2"), result.getJumps());
        assertEquals(0, result.getTotalFine());
    }

    private Trampoline[][] forceMapToTrampoline(int [][] forceMap){

        Trampoline[][] map = new Trampoline[forceMap.length][forceMap[0].length];

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                int finalI = i;
                int finalJ = j;

                map[i][j] = new Trampoline() {
                    @Override
                    public int getJumpForce() {
                        return forceMap[finalI][finalJ];
                    }

                    @Override
                    public Type getType() {
                        return Type.NORMAL;
                    }
                };
            }
        }

        return map;
    }

    /*
            int[][] forceMap = {
                {1, 2, 2},
                {2, 10, 1},
                {3, 2, 0}
        };
     */

}
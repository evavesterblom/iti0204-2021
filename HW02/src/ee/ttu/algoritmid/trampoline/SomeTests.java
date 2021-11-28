package ee.ttu.algoritmid.trampoline;

import java.util.List;

public class SomeTests {

    public static void main(String[] args) {
        int[][] forceMap = {
                {1, 2, 2},
                {2, 10, 1},
                {3, 2, 0}
        };

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

        HW02 solution = new HW02();

        Result gameResult = solution.play(map);

        List<String> defaultSolution = List.of("S1", "E2", "S1");
        int defaultTotalFine = 0;

        if (!gameResult.getJumps().equals(defaultSolution) || gameResult.getTotalFine() != defaultTotalFine) {
            System.err.println("= case does not work properly");
        } else {
            System.out.println("= case is fine");
        }

        List<String> advancedSolution = List.of("S2", "E2");
        List<String> advancedSolutionAlternative = List.of("E2", "S2");
        int advancedTotalFine = 0;

        if ((!gameResult.getJumps().equals(advancedSolution) && !gameResult.getJumps().equals(advancedSolutionAlternative))
                || gameResult.getTotalFine() != advancedTotalFine) {
            System.err.println("+- case does not work properly");
        } else {
            System.out.println("+- case is fine");
        }
    }
}


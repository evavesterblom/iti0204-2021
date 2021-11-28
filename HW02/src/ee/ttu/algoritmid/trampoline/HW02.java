package ee.ttu.algoritmid.trampoline;

import java.util.ArrayList;
import java.util.List;

public class HW02 implements TrampolineCenter {

    @Override
    public Result play(Trampoline[][] map) {
        Graph graph = new Graph();
        graph.createGraph(map);

        var start = graph.getStartVertex();
        var end = graph.getEndVertex();


        return new Result() {
            @Override
            public List<String> getJumps() {
                return new ArrayList<>(){};
            }

            @Override
            public int getTotalFine() {
                return 0;
            }
        };
    }
}
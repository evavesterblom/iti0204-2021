package ee.ttu.algoritmid.erdos;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.stream.Collectors;
public class AL05 {
    public Graph graph = new Graph();

    private class Graph {
        private Map<String, List<String>> graph = new HashMap<>();
        private Map<String, Integer> erdosNumbers = new HashMap<>();

        private void addDirectedEdge(String one, String other) {
            if (!graph.containsKey(one)) {
                List<String> edges = new ArrayList<>();
                edges.add(other);
                graph.put(one, edges);
            } else if (!graph.get(one).contains(other)) {
                graph.get(one).add(other);
            }
        }

        /**
         * Add undirected edge to the graph.
         *
         * @param one   one element of the edge
         * @param other the other element of edge
         */
        public void addEdge(String one, String other) {
            addDirectedEdge(one, other);
            addDirectedEdge(other, one);
            if (!erdosNumbers.containsKey(one)) {
                erdosNumbers.put(one, -1);
            }
            if (!erdosNumbers.containsKey(other)) {
                erdosNumbers.put(other, -1);
            }
        }

        /**
         * Return the graph.
         *
         * @return the internal graph structure.
         */
        public Map<String, List<String>> getGraph() {
            return graph;
        }

        /**
         * Perform breadth first search.
         *
         * @param  goal the goal vertex to find
         * @return the Erdos number of goal or -1 if there is no chain of coauthors leading from Erdös to goal.
         * Note that search should always start from "Paul Erdös" whose Erdös number is 0.
         */
        public Integer breadthFirstSearch(String goal) {

            if (!graph.containsKey(goal)){
                return -1;
            } ;

            //Djikstra
            erdosNumbers.put("Paul Erdös", 0);

            var unvisitedQueue = new ArrayList<String>();
            unvisitedQueue.add("Paul Erdös");

            var distance = 0;

            while(!unvisitedQueue.isEmpty()) {

                for (var unvisited : unvisitedQueue.toArray(new String[0])) {

                    var currentErdosNumber = erdosNumbers.get(unvisited);
                    if (currentErdosNumber > distance || currentErdosNumber == -1){
                        erdosNumbers.put(unvisited, distance);
                    }
                    for (var u : graph.get(unvisited)) {
                        if ((erdosNumbers.get(u)) == -1) {
                            unvisitedQueue.add(u);
                        }
                    }
                    unvisitedQueue.remove(unvisited);
                }
                distance = distance + 1;
            }

        return erdosNumbers.get(goal);

        }
    }


    /**
     * Use buildGraphAndFindErdosNumber to build a graph using the Graph class and then use its breadthFirstSearch to
     * return the Erdos number.
     *
     * @param  coauthors the list of people who have coauthored scientific papers as pairs
     *                 (e.g., [["Juhan", "Jaan"], ["Juhan", "Siiri"]] means that "Juhan" has published with "Jaan" and "Siiri")
     * @param  scientist the name of the scientist whose Erdös number needs to be determined
     * @return the Erdos number of the scientist or -1 if it can`t be done
     *
     */
    public Integer buildGraphAndFindErdosNumber(List<SimpleEntry<String, String>> coauthors,
                                                String scientist) {

        for (var coauthor: coauthors) {
            graph.addEdge(coauthor.getKey(), coauthor.getValue());
        }

        return graph.breadthFirstSearch(scientist);
    }
}

class Runner {


    public static void main(String[] args) throws Exception {

        var graph = new AL05();

        List<SimpleEntry<String, String>> coauthorsList = new ArrayList<SimpleEntry<String, String>>();
        SimpleEntry<String, String> entry = new SimpleEntry<String, String>("Paul Erdös", "Toomas");
        SimpleEntry<String, String> entry5 = new SimpleEntry<String, String>("Toomas", "Jaan");
        SimpleEntry<String, String> entry6 = new SimpleEntry<String, String>("Paul Erdös", "August");
        SimpleEntry<String, String> entry2 = new SimpleEntry<String, String>("August", "Karl");
        SimpleEntry<String, String> entry3 = new SimpleEntry<String, String>("Karl", "Juhan");
        SimpleEntry<String, String> entry4 = new SimpleEntry<String, String>("Juhan", "Jaan");
        SimpleEntry<String, String> entry7 = new SimpleEntry<String, String>("Vootele", "Mark");
        coauthorsList.add(entry);
        coauthorsList.add(entry2);
        coauthorsList.add(entry3);
        coauthorsList.add(entry4);
        coauthorsList.add(entry5);
        coauthorsList.add(entry6);
        coauthorsList.add(entry7);

        var answer  =  graph.buildGraphAndFindErdosNumber(coauthorsList, "Vootele");
        assert(answer == 1);
    }
}
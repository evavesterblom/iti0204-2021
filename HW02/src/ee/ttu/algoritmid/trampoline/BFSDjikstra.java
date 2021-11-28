package ee.ttu.algoritmid.trampoline;

import java.util.*;

public class BFSDjikstra {

    public HashMap<Vertex, Vertex> search(Vertex start, Vertex goal){

        if (start == null || goal == null) return null;

        var unvisitedQueue = new ArrayList<Vertex>();
        var distanceMap = new HashMap<Vertex, Integer>();
        var previousMap = new HashMap<Vertex, Vertex>();
        var distance = 0;
        var found = false;

        unvisitedQueue.add(start);

        while (!unvisitedQueue.isEmpty() && !found){
            for (var element : unvisitedQueue.toArray(new Vertex[0])) {
                unvisitedQueue.remove(element);

                if (element == goal) found = true;

                var connectedVertexes = element.getConnectedVertexes();
                unvisitedQueue.addAll(connectedVertexes);

                for (var v : connectedVertexes){

                    if(!distanceMap.containsKey(v)) unvisitedQueue.add(v);

                    //if found shorter distance
                    if(!distanceMap.containsKey(v)){

                        if(distance < distanceMap.get(v)){
                            distanceMap.put(v, distance);
                            previousMap.put(v, element);
                        }
                        //arvesta trahvi siin
                        if(distance == distanceMap.get(v)){
                            //peaks vaatama kas see distancemap key on trahviga ja kui suurega
                            //kui trahv on vaiksem, siis eelista uut teekonda
                        }
                    }
                }

                distance = distance + 1;
            }
        }

        return previousMap;
    }

    public List<Vertex> reversePathMap(HashMap<Vertex, Vertex> previousMap, Vertex source, Vertex goal){

        if (previousMap == null || source == null || goal == null) return null;

        var result = new LinkedList<Vertex>();
        var v = goal;
        if(previousMap.containsKey(goal) || source == goal){
            while(previousMap.containsKey(v)){
                result.push(v);
                v = previousMap.get(v);
            }
            result.push(source);
        }
        return result;
    }
}

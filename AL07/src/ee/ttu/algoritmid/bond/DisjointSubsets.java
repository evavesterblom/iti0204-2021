package ee.ttu.algoritmid.bond;

import java.util.TreeMap;

public class DisjointSubsets {

    private TreeMap<String, String> parent = new TreeMap<>(); //<key, parent>
    private TreeMap<String, Integer> rank = new TreeMap<>(); //<key, rank>

    public String find(String element) throws IllegalArgumentException {
        if (!(parent.containsKey(element))) throw new IllegalArgumentException("find: the element is not present");
        var parentElement = parent.get(element);
        if (!parentElement.equals(element)){
            parent.put(element, find(parentElement)); //compression
        }
        return parent.get(element);
    }

    public void union(String element1, String element2) throws IllegalArgumentException {
        if (!(parent.containsKey(element1)) || !(parent.containsKey(element2)))
        {
            throw new IllegalArgumentException("union: any of elements is not present");
        }
        var rootOfElement1 = find(element1);
        var rootOfElement2 = find(element2);


        if (rootOfElement1 == rootOfElement2) return; //they belong to same set, no union

        if (rank.get(rootOfElement1) > rank.get(rootOfElement2)){ //attach a smaller depth under the root of the bigger tree
            parent.put(rootOfElement2, rootOfElement1);
        }
        else if (rank.get(rootOfElement1) < rank.get(rootOfElement2)){
            parent.put(rootOfElement1, rootOfElement2);
        }
        else
        {
            parent.put(rootOfElement1, rootOfElement2);
            rank.put(rootOfElement2, rank.get(rootOfElement2) + 1);
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        if(parent.containsKey(element)) throw new IllegalArgumentException("addSubset: element is already present");
        parent.put(element, element);
        rank.put(element, 0);
    }
}
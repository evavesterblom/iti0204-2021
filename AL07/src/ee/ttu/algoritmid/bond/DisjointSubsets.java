package ee.ttu.algoritmid.bond;

import java.util.TreeMap;

public class DisjointSubsets {

    private TreeMap<String, String> parent = new TreeMap<>(); //<key, parent>
    private TreeMap<String, Integer> rank = new TreeMap<>(); //<key, rank>
    private TreeMap<String, AL07.Network> network = new TreeMap<>(); //<key, networkType>

    public String find(String element) throws IllegalArgumentException {
        if (!(parent.containsKey(element))) throw new IllegalArgumentException("find:  the element is not present");
        if (parent.get(element) != element){
            parent.put(element, find(parent.get(element))); //compression
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
        if (rank.get(rootOfElement1) < rank.get(rootOfElement2)){
            parent.put(rootOfElement1, rootOfElement2);
        }
        else
        {
            parent.put(rootOfElement1, rootOfElement2);
            rank.put(rootOfElement2, rank.get(rootOfElement2) + 1);
        }

        //network
        if (!network.containsKey(element1) && network.containsKey(rootOfElement1)) {
            network.put(element1, network.get(rootOfElement1));
        }
        if (!network.containsKey(element2) && network.containsKey(rootOfElement2)) {
            network.put(element2, network.get(rootOfElement2));
        }
        if (!network.containsKey(element1) && network.containsKey(element2)){
            network.put(element1, network.get(element2));
        }
        if (!network.containsKey(element2) && network.containsKey(element1)){
            network.put(element2, network.get(element1));
        }
    }

    public void addSubset(String element) throws IllegalArgumentException {
        if(parent.containsKey(element)) throw new IllegalArgumentException("addSubset: element is already present");
        parent.put(element, element);
        rank.put(element, 0);
        network.put(element, AL07.Network.UNKNOWN);
    }

    public AL07.Network getNetwork(String element){
        if (network.containsKey(element)){
            return network.get(element);
        }
        return null;
    }

    public void setNetwork(String element, AL07.Network networkType){
        network.put(element, networkType);
    }

}
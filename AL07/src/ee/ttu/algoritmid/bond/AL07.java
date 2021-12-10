package ee.ttu.algoritmid.bond;

import java.util.HashMap;

public class AL07 {

    public enum Network {
        FRIENDLY, UNFRIENDLY, UNKNOWN;
    }

    private DisjointSubsets disjointSubsets = new DisjointSubsets();

    public AL07() {
        // don't remove
        disjointSubsets.addSubset(Network.FRIENDLY.toString());
        disjointSubsets.addSubset(Network.UNFRIENDLY.toString());
        talkedToEachOther("A", Network.FRIENDLY.toString());
        talkedToEachOther("U", Network.UNFRIENDLY.toString());
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        try {disjointSubsets.find(name1);}
        catch (Exception e) {addPerson(name1);}

        try {disjointSubsets.find(name2);}
        catch (Exception e) {addPerson(name2);}

        disjointSubsets.union(name1, name2);
    }

    public void addPerson(String name) {
        disjointSubsets.addSubset(name);
    }

    public void friendly(String name) {
        try {disjointSubsets.find(name);}
        catch (Exception e) {addPerson(name);}
        disjointSubsets.union(name, Network.FRIENDLY.toString());
    }

    public void unfriendly(String name) {
        try {disjointSubsets.find(name);}
        catch (Exception e) {addPerson(name);}
        disjointSubsets.union(name, Network.UNFRIENDLY.toString());
    }

    public Network memberOfNetwork(String name) {
        var parent = disjointSubsets.find(name);

        try {
            return Network.valueOf(parent);
        }
        catch (IllegalArgumentException ex)
        {
            return Network.UNKNOWN;
        }
    }
}
package ee.ttu.algoritmid.bond;

public class AL07 {

    public enum Network {
        FRIENDLY, UNFRIENDLY, UNKNOWN;
    }

    private DisjointSubsets disjointSubsets = new DisjointSubsets();

    public AL07() {
        // don't remove
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        getDisjointSubsets().union(name1, name2);
    }

    public void addPerson(String name) {
        getDisjointSubsets().addSubset(name);
    }

    public void friendly(String name) {
        // TODO
    }

    public void unfriendly(String name) {
        // TODO
    }

    public Network memberOfNetwork(String name) {
        // TODO
        return null;
    }

}
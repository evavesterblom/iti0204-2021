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
        try {disjointSubsets.find(name1);}
        catch (Exception e) {addPerson(name1);}

        try {disjointSubsets.find(name2);}
        catch (Exception e) {addPerson(name2);}

        disjointSubsets.union(name1, name2);

    }

    private void addNetwork(String name, Network network){
        disjointSubsets.setNetwork(name, network);
    }

    public void addPerson(String name) {
        disjointSubsets.addSubset(name);
    }

    public void friendly(String name) {
        disjointSubsets.setNetwork(name, Network.FRIENDLY);
    }

    public void unfriendly(String name) {
        disjointSubsets.setNetwork(name, Network.UNFRIENDLY);
    }

    public Network memberOfNetwork(String name) {
        return disjointSubsets.getNetwork(name);
    }
}
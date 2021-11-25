package ee.ttu.algoritmid.flights;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


class Node {
    double key;
    Node left, right;

    ArrayList<FlightCrewMember> availableCoPilots = new ArrayList<>();
    ArrayList<FlightCrewMember> availablePilots = new ArrayList<>();
    ArrayList<FlightCrewMember> availableFlightAttendants = new ArrayList<>();
    ArrayList<String> testList = new ArrayList<>();

    Node(double k) {
        key = k;
    }
}

class BSTTree {

    private Node root;


    public Node getRoot(){
        return  root;
    }

    public Node get(double key){
        return get(root, key);
    }

    public boolean containsKey(double key){
        return get(root, key) != null;
    }

    public void put(double key){
        if (root == null) {
            root = new Node(key);
            return;
        }

        put(root, key);
    }

    public void delete(double key){
        delete(root, key);
    }

    public ArrayList<Node> treeWalkInorder(boolean isReversed){
        ArrayList list = new ArrayList();
        treeWalkInorder(root, list, isReversed);
        return list;
    }

    public ArrayList<Node> treeWalkInorderRange(double fromKey, double toKey, boolean isReversed){
        ArrayList list = new ArrayList();
        treeWalkInorderRange(root, list, fromKey, toKey, isReversed);
        return list;
    }


    private void treeWalkInorderRange(Node node, ArrayList<Node> list, double fromKey, double toKey, boolean isReversed){
        if (node == null) {
            return;
        }
        if (isReversed){
            if (node.key <= toKey) treeWalkInorderRange(node.right, list, fromKey, toKey, isReversed);
            if (node.key >= fromKey && node.key <= toKey) list.add(node);
            if (node.key >= fromKey) treeWalkInorderRange(node.left, list, fromKey, toKey, isReversed);
        }
        else {
            if (node.key >= fromKey) treeWalkInorderRange(node.left, list, fromKey, toKey, isReversed);
            if (node.key >= fromKey && node.key <= toKey) list.add(node);
            if (node.key <= toKey) treeWalkInorderRange(node.right, list, fromKey, toKey, isReversed);
        }
    }

    private void treeWalkInorder(Node node, ArrayList<Node> list, boolean reverse) {
        if (node == null) {
            return;
        }
        if (reverse){
            treeWalkInorder(node.right, list, reverse);
            list.add(node);
            treeWalkInorder(node.left, list, reverse);
        }
        else {
            treeWalkInorder(node.left, list, reverse);
            list.add(node);
            treeWalkInorder(node.right, list, reverse);
        }
    }

    private Node put(Node node, double key){
        if (node == null) return (new Node(key));
        if (key < node.key) node.left = put(node.left, key);
        if (key > node.key) node.right = put(node.right, key);
        if (key == node.key) return node;

        return node;
    }

    private Node get(Node node, double key){
        if (node == null) return null;
        if (key == node.key) return node;
        if (key < node.key) return get(node.left, key);
        if (key > node.key) return get(node.right, key);
        return null;
    }

    private Node delete(Node node, double key){
        if (node == null) return null;
        if (key < node.key) node.left = delete(node.left, key);
        if (key > node.key) node.right =  delete(node.right, key);
        if (key == node.key){
            //no child or 1 child
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;

            //2.child
            var successor = getSuccessor(node.right);
            node.key = successor.key;
            node.availableCoPilots = successor.availableCoPilots;
            node.availablePilots = successor.availablePilots;
            node.availableFlightAttendants = successor.availableFlightAttendants;

            // successor delete and add right subtree to node right
            node.right = delete(node.right, node.key);

        }
        return node;
    }

    private Node getSuccessor(Node node) {
        while (node.left != null)
        {
            node = node.left;
        }
        return node;
    }
}

public class FlightCrewMemberQueueBst{

    private final BSTTree availableMembersQueue = new BSTTree();

    public void removeFromQueue(FlightCrewMember participant){
        var seniority = participant.getWorkExperience();
        var foundNode = availableMembersQueue.get(seniority);

        if (foundNode == null) return;

        ArrayList<FlightCrewMember> flightCrewMembers = null;
        switch (participant.getRole()){
            case PILOT -> flightCrewMembers = foundNode.availablePilots;
            case COPILOT -> flightCrewMembers = foundNode.availableCoPilots;
            case FLIGHT_ATTENDANT -> flightCrewMembers = foundNode.availableFlightAttendants;
        }

        flightCrewMembers.remove(participant);

        if (foundNode.availableFlightAttendants.isEmpty() &&
            foundNode.availablePilots.isEmpty() &&
            foundNode.availableCoPilots.isEmpty()){
            availableMembersQueue.delete(seniority);
        }
    }

    public void addToQueue(FlightCrewMember participant){

        availableMembersQueue.put(participant.getWorkExperience());

        var foundNode = availableMembersQueue.get(participant.getWorkExperience());

        switch (participant.getRole()){
            case COPILOT -> foundNode.availableCoPilots.add(participant);
            case PILOT ->  foundNode.availablePilots.add(participant);
            case FLIGHT_ATTENDANT ->  foundNode.availableFlightAttendants.add(participant);
        }

        return;
    }

    public List<FlightCrewMember> getAllMembersFromQueueSorted(){

        var nodeList = availableMembersQueue.treeWalkInorder(false);

        ArrayList<FlightCrewMember> resultList = new ArrayList();

        for (var node : nodeList) {
            resultList.addAll(node.availableFlightAttendants);
            resultList.addAll(node.availableCoPilots);
            resultList.addAll(node.availablePilots);
        }

        return resultList;
    }

    public ArrayList<FlightCrewMember> getFromQueueByRange(
            FlightCrewMember.Role roleToFind,
            double fromSeniority,
            double toSeniority,
            boolean fromInclusive,
            boolean toInclusive,
            boolean isReversed){

        var nodes = availableMembersQueue.treeWalkInorderRange(
                fromSeniority,
                toSeniority,
                isReversed
        );

        ArrayList<FlightCrewMember> flightCrewMembers = new ArrayList<>();
        for (var node : nodes) {
            switch (roleToFind) {
                case COPILOT -> flightCrewMembers.addAll(node.availableCoPilots);
                case PILOT -> flightCrewMembers.addAll(node.availablePilots);
                case FLIGHT_ATTENDANT -> flightCrewMembers.addAll(node.availableFlightAttendants);
            }
        }
        return flightCrewMembers;
    }






    //TREE CREATE
    public static void main(String[] args) {
        BSTTree tree = new BSTTree();

        tree.put(5.0);
        tree.get(5.0).testList.add(0, "viis");

        tree.put(3.0);
        tree.get(3.0).testList.add(0, "kolm-tst");
        tree.put(9.0);
        tree.get(9.0).testList.add(0, "yheksa-tst");
        tree.put(7.0);
        tree.get(7.0).testList.add(0, "seitse-tst");
        tree.put(19.0);
        tree.get(19.0).testList.add(0, "yheksateist-tst");
        tree.put(6.0);
        tree.get(6.0).testList.add(0, "kuus-tst");
        tree.put(20.0);
        tree.get(20.0).testList.add(0, "kakskend-tst");
        tree.delete(20.0);

        var treeAsc= tree.treeWalkInorder(false);
        var treeDesc= tree.treeWalkInorder(true);
        var rangeAsc = tree.treeWalkInorderRange(3.1, 18.9, false);
        var rangeDesc = tree.treeWalkInorderRange(3.1, 18.9, true);

        double koma = 135.9999999999999;

        var g = tree.get(3.0);
        var ggg = tree.get(2.99999999999999999999999999999999999999999999999);
        var gg = tree.get(3.3);

        var a = tree.containsKey(4.9999999999999999999999999999999999999999);
        var b = tree.containsKey(10.5556783333333);

        var myRoot = tree.getRoot();

        var stop = 1;
    }
}

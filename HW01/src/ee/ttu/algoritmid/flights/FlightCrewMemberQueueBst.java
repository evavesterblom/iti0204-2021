package ee.ttu.algoritmid.flights;

import java.util.ArrayList;
import java.util.List;


class Node {
    double key;
    Node left, right;
    int height;

    ArrayList<FlightCrewMember> availableCoPilots = new ArrayList<>();
    ArrayList<FlightCrewMember> availablePilots = new ArrayList<>();
    ArrayList<FlightCrewMember> availableFlightAttendants = new ArrayList<>();
    ArrayList<String> testList = new ArrayList<>();

    Node(double k) {
        key = k;
        height = 1;
    }
}

class BSTTree {

    private Node root;

    private boolean doBalancing;

    BSTTree(boolean doBalancing){
        this.doBalancing = doBalancing;
    }


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

        root = put(root, key);
    }

    public void delete(double key){
        root = delete(root, key);
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

        //Balance BST
        if (doBalancing) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            var balance = getBalance(node);

            if (balance > 1 && key < node.left.key) return rotateRight(node); //Left-left rotation
            if (balance < -1 && key > node.right.key) return rotateLeft(node); //Right-right rotation
            if (balance > 1 && key > node.left.key){
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            if (balance < -1 && key < node.right.key){
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }
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

        //Balance BST
        if (doBalancing) {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
            var balance = getBalance(node);

            if (balance > 1 && getBalance(node.left) >= 0) return rotateRight(node); //Left-left rotation
            if (balance < -1 && getBalance(node.right) <= 0) return rotateLeft(node); //Right-right rotation
            if (balance > 1 && getBalance(node.left) < 0){ //Left-right rotation
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            if (balance < -1 && getBalance(node.right) > 0){//Right-left rotation
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
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

    private int getHeight(Node node){
        if (node == null) return 0;
        return node.height;
    }

    private int getBalance(Node node){
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    private Node rotateRight(Node a){

        Node newRoot = a.left;
        Node newRootLeftChild = newRoot.right;

        newRoot.right = a;
        a.left = newRootLeftChild;

        a.height = Math.max(getHeight(a.right), getHeight(a.left)) + 1;
        newRoot.height  = Math.max(getHeight(newRoot.right), getHeight(newRoot.left)) + 1;

        return newRoot;
    }

    private Node rotateLeft(Node a){
        Node newRoot = a.right;
        Node newRootLeftChild = newRoot.left;

        newRoot.left = a;
        a.right = newRootLeftChild;

        a.height = Math.max(getHeight(a.right), getHeight(a.left)) + 1;
        newRoot.height  = Math.max(getHeight(newRoot.right), getHeight(newRoot.left)) + 1;

        return newRoot;
    }
}

public class FlightCrewMemberQueueBst{

    private final BSTTree availableMembersQueue = new BSTTree(true);

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
        BSTTree tree = new BSTTree(true);

        //Left rotate
        /*
        tree.put(5.0);
        tree.get(5.0).testList.add(0, "5");
        tree.put(3.0);
        tree.get(3.0).testList.add(0, "3-tst");
        tree.put(6.0);
        tree.get(6.0).testList.add(0, "6-tst");
        tree.put(2.0);
        tree.get(2.0).testList.add(0, "2-tst");
        tree.put(3.5);
        tree.get(3.5).testList.add(0, "3.5-tst");
        tree.put(1.5);
        tree.get(1.5).testList.add(0, "1.5-tst");
         */

        /// Right rotate
        /*
        tree.put(5.5);
        tree.get(5.5).testList.add(0, "5.5-tst");
        tree.put(6.5);
        tree.get(6.5).testList.add(0, "6.5-tst");
        tree.put(7.5);
        tree.get(7.5).testList.add(0, "7.5-tst");
        */

        //Left right rotate
/*
        tree.put(9.0);
        tree.get(9.0).testList.add(0, "9");
        tree.put(4.0);
        tree.get(4.0).testList.add(0, "4");
        tree.put(7.0);
        tree.get(7.0).testList.add(0, "7");

 */


        //Right left rotate
        /*
        tree.put(8.0);
        tree.get(8.0).testList.add(0, "8");
        tree.put(10.0);
        tree.get(10.0).testList.add(0, "10");
        tree.put(9.0);
        tree.get(9.0).testList.add(0, "9");
         */

        tree.put(0.1);
        tree.get(0.1).testList.add(0, "0.1");
        tree.put(3.9);
        tree.get(3.9).testList.add(0, "3.9");
        tree.put(2.0);
        tree.get(2.0).testList.add(0, "2.0");
        tree.put(6.6);
        tree.get(6.6).testList.add(0, "6.6");
        tree.put(9.0);
        tree.get(9.0).testList.add(0, "9.0");

        //tree.delete(1.0);


        //tree.put(20.0);
        //tree.get(20.0).testList.add(0, "kakskend-tst");
        //tree.delete(20.0);

        var stopHere = "stop";

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

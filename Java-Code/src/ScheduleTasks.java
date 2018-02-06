import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class ScheduleTasks extends Schedule {
    final static boolean DEBUG = Test.DEBUG;
    final static String pathname = "sample.txt";

    public ScheduleTasks() {
        super(pathname);
    }

    public void optimalSolution () {
        Node aNode = new Node();
    }

}

class Node {
    final static boolean DEBUG = Test.DEBUG;

    private Node parent;
    private int cost;
    private int level;
    private List<Node> children = new ArrayList<Node>(); 			//Does this need to have its type changed?
    public List<Integer> currentSet;
    private int task;
    /**
     * The null node, used as the level 0/starting point of the tree
     */
    public Node() {
        this.level = 0;
        currentSet = new ArrayList<Integer>();
        currentSet.add(1);
        currentSet.add(2);
        currentSet.add(3);
        currentSet.add(4);
        //currentSet.add(5);
        //currentSet.add(6);
        //currentSet.add(7);
        //currentSet.add(8);
        genChildren();
    }

    /**
     * Constructor for nodes
     * @param parent The node before the current level
     * @param task
     * @param currentSet
     * @param level The current level
     *
     */
    public Node(Node parent, int task, List<Integer> currentSet, int level) {
        this.parent = parent;
        this.task = task;
        this.currentSet = new ArrayList<Integer>(currentSet);
        this.level = level;
        System.out.println("Machine: " + level + " Task: " + task);
        genChildren();
    }

    /*
     * Getter for parent of the given node
     */
    public Node getParent() {
        return parent;
    }
    /*
     * Getter for cost of the given node
     */
    public double getCost() {
        return cost;
    }
    /*
     * Getter for level of the given node
     */
    public int getLevel() {
        return level;
    }
    /*
     *Returns the array up to the point it is called
     */
    public int[] path(int[] path, int level){

        return path;
    }
    public void genChildren(){
        if (level!=0) {
            currentSet.remove((currentSet.indexOf(this.task)));
        }
        for (int i=0; i<4-level;i++) {
            Node aNode = new Node(this, currentSet.get(i), currentSet, level+1);
            children.add(aNode);
        }



    }

}


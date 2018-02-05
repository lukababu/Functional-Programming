import java.util.*;

public class Node {
	private Node parent;
	private int cost;
	private int level;
	private Node[] children; 			//Does this need to have its type changed?
	public List<Integer> currentSet;

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
		currentSet.add(5);
		currentSet.add(6);
		currentSet.add(7);
		currentSet.add(8);
	}

	/**
	 * Constructor for nodes
	 * @param parent The node before the current level
	 * @param children
	 * @param cost
	 * @param level The current level
	 * 
	 */
	public Node(Node parent, Node[] children, int cost, int level) {
		this.parent = parent;
		this.children = children;
		this.cost = cost;
		this.level = level;
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
	public Node[] genChildren(){
		//Node[] children = new Node[currentSet.length - 1];
		
		 return children;
		
				
	}
	
}

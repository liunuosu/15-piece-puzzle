import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Class that solves a puzzle using the A* method
 * 
 */
public class A_star {
	TreeMap<Integer, Node> openSet = new TreeMap<Integer, Node>();
	TreeMap<Integer, Node> closedSet = new TreeMap<Integer, Node>();
	public ArrayList<Node> pathSet = new ArrayList<>();
	Node rootNode;
	String method = "greedy";

	/**
	 * Constructor of the A_star class
	 * 
	 * @param puzzle input puzzle that is to be solved
	 */
	public A_star(Node puzzle) {
		this.rootNode = puzzle;
		solve();
	}

	/**
	 * Solves for the initial puzzle
	 * 
	 */
	public void solve() {

		// Check whether the input puzzle is solvable
		if (rootNode.solvable(rootNode.getBoard())) {
			openSet.put(rootNode.getKey(), rootNode);
			int currentKey =  rootNode.getKey();
			int nextKey = 0;

			while (openSet.size() > 0) {
				
				// At each iteration we look for a child configuration with the best f score
				for (Map.Entry<Integer, Node> entry : openSet.entrySet()) {
					if (entry.getValue().getF() < openSet.get(currentKey).getF()) {
						currentKey = entry.getKey();
					} else {
						nextKey = entry.getKey();
					}
				}

				Node current = openSet.get(currentKey);

				// We then check whether this configuration is the final configuration
				if (current.checkIfComplete()) {
					openSet.clear();
					pathSet.add(current);
					Node parent = current.getParent();
					while (!parent.isTheSame(this.rootNode.getBoard())) {
						pathSet.add(parent);
						parent = parent.getParent();
					}
					pathSet.add(rootNode);
					break;
				}

				// If not, we remove the current node from the nodes to be checked and add it to
				// the set of nodes that have already been considered
				openSet.remove(currentKey);
				closedSet.put(currentKey, current);
				currentKey = nextKey;

				// Furthermore, we add its child configurations that are not already considered
				// to the open set
				current.addChildren();
				for (Node child : current.getChildren()) {
					child.setH(method);
					if(!closedSet.containsKey(child.getKey()) && !openSet.containsKey(child.getKey())) {
						openSet.put(child.getKey(), child);
						currentKey =  child.getKey();
					}
				}

			}
		}
	}

	
	/**
	 * Prints the solution
	 *  
	 */
	public void printSolution() {
		for(int i = this.pathSet.size() - 1; i >=0; i--) {
			this.pathSet.get(i).printPuzzle();
			System.out.println("");
		}
	}
	
	/**
	 * returns the path
	 *  
	 */
	public ArrayList<Node> getPathSet() {
		return pathSet;
	}
	
}

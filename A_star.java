import java.util.ArrayList;

/**
 * Class that solves a puzzle using the A* method
 * 
 */
public class A_star {
	public ArrayList<Node> openSet = new ArrayList<>();
	public ArrayList<Node> closedSet = new ArrayList<>();
	public ArrayList<Node> pathSet = new ArrayList<>();
	Node rootNode;

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
			openSet.add(this.rootNode);

			while (openSet.size() > 0) {
				// At each iteration we look for a child configuration with the best f score
				int currentIndex = 0;
				for (int i = 1; i < openSet.size(); i++) {
					if (openSet.get(i).getF() < openSet.get(currentIndex).getF()) {
						currentIndex = i;
					}
				}

				Node current = openSet.get(currentIndex);

				// We then check whether this configuration is the final configuration
				if (!current.checkIfComplete()) {
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
				openSet.remove(currentIndex);
				closedSet.add(current);

				// Furthermore, we add its child configurations that are not already considered
				// to the open set
				current.addChildren();
				for (Node child : current.getChildren()) {
					if (!inSet(this.openSet, child) && !inSet(this.closedSet, child)) {
						openSet.add(child);
					}

				}
			}
		}
	}

	/**
	 * Method to check whether a given node is contained in a given set
	 * 
	 * @param set in which node n might be
	 * @param n   node that is possibly in the set
	 */
	private boolean inSet(ArrayList<Node> set, Node n) {
		for (Node i : set) {
			if (n.isTheSame(i.getBoard())) {
				return true;
			}
		}
		return false;
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
	
	//ADDED BY KES
	public ArrayList<Node> getPathSet() {
		return pathSet;
	}
	

}

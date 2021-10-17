import java.util.ArrayList;

/**
 * Extension of the GameBoard class that is specifically used in the A*
 * algorithm
 * 
 */
public class Node extends GameBoard {
	private static ArrayList<Node> children = new ArrayList<>();
	private Node parent = this;
	private int g = 0;
	private int h = 0;
	private int f = 0;
	private int key = 0;
	
	/**
	 * Constructor of the Node class
	 * 
	 * @param configuration
	 * @param g             g-score of the input configuration. The g-score is equal
	 *                      to the amount of iterations it takes to get from the
	 *                      initial configuration to the current configuration
	 */
	public Node(ArrayList<Integer> configuration, int g, int h) {
		super(configuration);
		this.g = g;
		this.h = h; // Heuristic score
		this.f = this.g + this.h;
		int p = 1;
		for(int i = 0; i < 16; i++) {
			this.key += configuration.get(i)*p;
			p = p*10; 
		}
	}

	public void setH(String method) {
		if(method == "greedy") {
			this.f = this.h;
		} else {
			this.f = this.g + this.h;
		}
	}
	
	/**
	 * Getter method for the children
	 * 
	 */
	public ArrayList<Node> getChildren() {
		return this.children;
	}

	/**
	 * Getter method for the parent
	 * 
	 */
	public Node getParent() {
		return this.parent;
	}

	/**
	 * Getter method for the f-score
	 * 
	 */
	public int getF() {
		return this.f;
	}

	public int getG() {
		return this.g;
	}

	public int getH() {
		return this.h;
	}
	
	public int getKey() {
		return this.key;
	}
	
	public boolean hasKey(int key) {
		if(this.key == key) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Method to create a configuration like the current one, but with the blank
	 * tile moved to the right
	 * 
	 * @param c configuration to be moved
	 */
	public void moveRight(ArrayList<Integer> c) {
		int x = this.getLocationOfZero();
		if (x % this.getHeight() < this.getHeight() - 1) {
			ArrayList<Integer> newC = swap(c, x, "right");
			int h = linearConflict(newC);
			addChild(newC, this.g + 1, h);
		}
	}

	/**
	 * Method to create a configuration like the current one, but with the blank
	 * tile moved to the left
	 * 
	 * @param c configuration to be moved
	 */
	public void moveLeft(ArrayList<Integer> c) {
		int x = this.getLocationOfZero();
		if (x % this.getHeight() > 0) {
			ArrayList<Integer> newC = swap(c, x, "left");
			int h = linearConflict(newC);
			addChild(newC, this.g + 1, h);
		}
	}

	/**
	 * Method to create a configuration like the current one, but with the blank
	 * tile moved up
	 * 
	 * @param c configuration to be moved
	 */
	public void moveUp(ArrayList<Integer> c) {
		int x = this.getLocationOfZero();
		if (x > this.getHeight() - 1) {
			ArrayList<Integer> newC = swap(c, x, "up");
			int h = linearConflict(newC);
			addChild(newC, this.g + 1, h);
		}

	}

	/**
	 * Method to create a configuration like the current one, but with the blank
	 * tile moved down
	 * 
	 * @param c configuration to be moved
	 */
	public void moveDown(ArrayList<Integer> c) {
		int x = this.getLocationOfZero();
		if (x < this.getHeight() * (this.getHeight() - 1)) {
			ArrayList<Integer> newC = swap(c, x, "down");
			int h = linearConflict(newC);
			addChild(newC, this.g + 1, h);
		}

	}

	/**
	 * Swaps two integers in the array
	 * 
	 * @param array    the array to be swapped
	 * @param position the position to be swapped
	 * @return int[] with updated positions
	 */
	public ArrayList<Integer> swap(ArrayList<Integer> array, int position, String direction) {
		ArrayList<Integer> swappedArray = new ArrayList<>(array);

		int newPosition = 0;
		if (direction == "up") {
			newPosition = position - this.getHeight();
		} else if (direction == "down") {
			newPosition = position + this.getHeight();
		} else if (direction == "left") {
			newPosition = position - 1;
		} else if (direction == "right") {
			newPosition = position + 1;
		}

		// swaps input position with next
		int temp = swappedArray.get(position);
		swappedArray.set(position, swappedArray.get(newPosition));
		swappedArray.set(newPosition, temp);

		return swappedArray;

	}

	/**
	 * Creates a child node, linking it to its parent
	 * 
	 * @param configuration configuration of the child node
	 * @param gScore        g score of the child node
	 */
	public void addChild(ArrayList<Integer> configuration, int gScore, int hScore) {
		Node child = new Node(configuration, gScore, hScore);
		// adds child to list of children
		this.children.add(child);
		// links to parent node
		child.parent = this;

	}

	/**
	 * Method to create every possible child node of the current configuration
	 * 
	 */
	public void addChildren() {
		this.moveRight(this.getBoard());
		this.moveLeft(this.getBoard());
		this.moveUp(this.getBoard());
		this.moveDown(this.getBoard());
	}

	/**
	 * Method to check whether the current configuration is the same as a given
	 * input configuration
	 * 
	 * @param c configuration to compared
	 */
	public boolean isTheSame(ArrayList<Integer> c) {
		if (this.getBoard().equals(c)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to calculate a heuristic based on the Manhattan distance of every
	 * non-blank tile in the current configuration to its final location
	 * 
	 */
	public int manhattan(ArrayList<Integer> c) {
		int m = 0;
		for (int i = 0; i <= this.getHeight() * (this.getHeight() - 1); i += this.getHeight()) {
			for (int j = 0; j < this.getHeight(); j++) {
				int value = c.get(i + j);
				if (value != 0) {
					int y1 = (value - 1) / this.getHeight();
					int y2 = (i + j) / this.getHeight();
					int x1 = (value - 1) % this.getHeight();
					int x2 = (i + j) % this.getHeight();
					m += Math.abs(y1 - y2) + Math.abs(x1 - x2);
				}
			}
		}

		return m;
	}

	/**
	 * Method to calculate a heuristic based on the number of linear conflicts in
	 * the current configuration
	 * 
	 */
	public int linearConflict(ArrayList<Integer> c) {
		int linearConflicts = 0;
		// For every row and column, we search for the tile with the most conflicts. We
		// then remove this tile and look again for the tile with the most conflicts and
		// so on until there are no conflicting tiles anymore. For each row and column,
		// the number of tiles that need to be removed so that there are no conflicts
		// anymore is added to the total number of linear conflicts.
		for (int ri = 0; ri < this.getHeight(); ri++) {

			ArrayList<Integer> newConfigurationRow = new ArrayList<>(c);
			int maxRowConflictor = maxRowConflictor(newConfigurationRow, ri);
			while (getRowConflicts(maxRowConflictor, newConfigurationRow) > 0) {
				linearConflicts++;
				newConfigurationRow.set(maxRowConflictor, 0);
			}

			ArrayList<Integer> newConfigurationCol = new ArrayList<>(c);
			int maxColConflictor = maxColConflictor(newConfigurationRow, ri);
			while (getColumnConflicts(maxColConflictor, newConfigurationCol) > 0) {
				linearConflicts++;
				newConfigurationCol.set(maxColConflictor, 0);
			}

		}
		return 2 * linearConflicts + this.manhattan(c);
	}

	/**
	 * Method to retrieve the index of the tile with the most linear conflicts in a
	 * given row in a given configuration
	 * 
	 * @param configuration
	 * @param row
	 */
	public int maxRowConflictor(ArrayList<Integer> configuration, int row) {
		int index = row * this.getHeight();
		int maxConflicts = getRowConflicts(index, configuration);
		for (int i = row * this.getHeight(); i < row * this.getHeight() + this.getHeight(); i++) {
			if (getRowConflicts(i, configuration) > maxConflicts) {
				maxConflicts = getRowConflicts(i, configuration);
				index = i;
			}
		}
		return index;
	}

	/**
	 * Method to retrieve the index of the tile with the most linear conflicts in a
	 * given column in a given configuration
	 * 
	 * @param configuration
	 * @param col           column
	 */
	public int maxColConflictor(ArrayList<Integer> configuration, int col) {
		int index = col * this.getHeight();
		int maxConflicts = getColumnConflicts(index, configuration);
		for (int i = col; i <= col + (this.getHeight() - 1) * this.getHeight(); i += this.getHeight()) {
			if (getColumnConflicts(i, configuration) > maxConflicts) {
				maxConflicts = getColumnConflicts(i, configuration);
				index = i;
			}
		}
		return index;
	}

	/**
	 * Method to retrieve the number of linear conflicts that a given tile has in
	 * its row
	 * 
	 * @param index         of the tile
	 * @param configuration
	 */
	public int getRowConflicts(int index, ArrayList<Integer> configuration) {
		int row = (index / this.getHeight());
		int rowConflicts = 0;
		if (configuration.get(index) != 0 && isInFinalRow(index, configuration)) { // Check if the element is in its
																					// final row
			for (int i = row * this.getHeight(); i < row * this.getHeight() + this.getHeight(); i++) {
				if (isInFinalRow(i, configuration) && i < index && configuration.get(i) > configuration.get(index)) {
					rowConflicts++;
					// System.out.println("conflict at index " + i + " (" + this.getBoard().get(i) +
					// ") and " + index + " (" + this.getBoard().get(index) + ") (row " + row +")");
				}
			}
		}
		return rowConflicts;
	}

	/**
	 * Method to retrieve the number of linear conflicts that a given tile has in
	 * its column
	 * 
	 * @param index         of the tile
	 * @param configuration
	 */
	public int getColumnConflicts(int index, ArrayList<Integer> configuration) {
		int col = index % this.getHeight();
		int colConflicts = 0;
		if (configuration.get(index) != 0 && isInFinalCol(index, configuration)) { // Check if the element is in its
																					// final collumn
			for (int i = col; i <= col + (this.getHeight() - 1) * this.getHeight(); i = i + this.getHeight()) {
				if (isInFinalCol(i, configuration) && i < index && configuration.get(i) > configuration.get(index)) {
					colConflicts++;
					// System.out.println("conflict at index " + i + " and " + index);
				}
			}
		}
		return colConflicts;
	}

	/**
	 * Method to check whether a tile at a given index is in its final row
	 * 
	 * @param index         of the tile
	 * @param configuration
	 */
	public boolean isInFinalRow(int index, ArrayList<Integer> configuration) {
		boolean isInFinalRow = false;
		if (configuration.get(index) != 0
				&& index / this.getHeight() == (configuration.get(index) - 1) / this.getHeight()) {
			isInFinalRow = true;
		}
		return isInFinalRow;
	}

	/**
	 * Method to check whether a tile at a given index is in its final column
	 * 
	 * @param index         of the tile
	 * @param configuration
	 */
	public boolean isInFinalCol(int index, ArrayList<Integer> configuration) {
		boolean isInFinalCol = false;
		if (configuration.get(index) != 0
				&& index % this.getHeight() == (configuration.get(index) - 1) % this.getHeight()) {
			isInFinalCol = true;
		}
		return isInFinalCol;
	}

}

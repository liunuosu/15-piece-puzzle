import java.util.ArrayList;

public class HumanAlgorithm {

	private GameBoard board;
	private ArrayList<String> moves;
	private ArrayList<ArrayList<Integer>> movesPuzzle = new ArrayList<ArrayList<Integer>>(); 
	private int RowsSolved;
	private int ColumnsSolved;
	private int numRow;
	private int numCol;

	/**
	 * Main method, used for testing the algorithm
	 * @param args 
	 */
	public static void main(String args[]) {
		//Test algorithm
		for(int i = 0; i < 1000000; i++) {
			Puzzle myPuzzle = new Puzzle();
			GameBoard game = new GameBoard(myPuzzle.getPuzzle());
			HumanAlgorithm person = new HumanAlgorithm(game);
			person.solve();
			System.out.println("Final state: " + game.getBoard());
		}
	}
	
	/**
	 * Instructor, called if Human Algorithm is used. Initializes the starting board, moves list, 
	 * 				and amount of columns and rows solved
	 * @param start
	 */
	public HumanAlgorithm(GameBoard start) {
		//Initialize starting board, moves list, amount of columns/rows solved, number of row/col in the puzzle
		this.board = start;
		moves = new ArrayList<String>();
		RowsSolved = 0;
		ColumnsSolved = 0;
		numRow = start.getHeight();
		numCol = numRow; //Same dimension typically, can be modified for different amount of columns
		addMove();
	}
	
	/**
	 * Method called to solve the puzzle
	 */
	public  void solve() { 

		//First solve for the rows except the last two
		for(int i = 1; i <= (numRow-2)*numCol; i++) {
			
			//If it's the last integer of the column's turn in a row, amount of rows solved increases
			if(i%numCol==0) {
				RowsSolved++;
			}
			//If integer is not in right place yet, position empty cell
			if(board.getPositionOf(i) != i - 1) {
				PositionEmptyCell(i);
				
				//If the integer is in correct place by repositioning empty cell, move on to the next integer
				if(board.getPositionOf(i) == i - 1) {
					continue;
				}
				
				//If the integer is the last integer of the row, another combination is needed
				if(i%numCol == 0) {
					BringIntegerRight(i);
					BringIntegerUp(i);
					BringLastIntegerOfRow(i);
					continue;
				}
				//For the other integers, the main procedure can be applied
				//Bring Integer to the right, then up, then slide it to the left to place it 
				//without disturbing other integers already in place
				BringIntegerRight(i);
				BringIntegerUp(i);
				BringIntegerLeft(i);
			}
		}
		//Solve for bottom two rows, solve the columns except the last two
		for(int i = (numRow-2)*numCol + 1; i <= (numRow-2)*numCol + numCol - 2; i++) {	
			//Integer i and the integer below it should be solved simultaneously 
			SetUp(i, i + numCol);
		}
		//solve final 4 squares
		RotateFinal();
	}

	/**
	 * Adds the current position of the board to the list keeping all the positions
	 */
	public void addMove() {
		ArrayList<Integer> sub = new ArrayList<Integer>(board.getBoard());
		movesPuzzle.add(sub);
	}
	
	/**
	 * Method that returns the list of all board positions made to get to the solved state
	 * @return List of all board positions between the starting and final positions
	 */
	public ArrayList<ArrayList<Integer>> getMoves() {
		return movesPuzzle;
	}
	
	/**
	 * Shuffles the last integer of the row into place, in a 4x4 case this is run for 4 and 8
	 * This special algorithm is needed since we cannot bring this integer up in the conventional way
	 * @param i
	 */
	public void BringLastIntegerOfRow(int i) {
		
		//If empty cell is below the integer, place it to the left
		if(board.getPositionOf(i) + numCol == board.getPositionOf(0)) {
			board.move("left");	moves.add("left"); 	addMove();
			board.move("up");	moves.add("up"); 	addMove();
		}
		//General sequence of moves for sliding the integer in (starts from the position left of the integer)
		
		for(int j = 0; j < numCol - 2; j++) {
			board.move("left");		moves.add("left");		addMove();	
		}
		board.move("up");		moves.add("up");		addMove();
		
		for(int j = 0; j < numCol - 1; j++) {
			board.move("right");	moves.add("right");		addMove();
		}
		board.move("down");		moves.add("down");		addMove();
		board.move("left");		moves.add("left");		addMove();
		board.move("up");		moves.add("up");		addMove();
		
		for(int j = 0; j < numCol - 2; j++) {
			
			board.move("left");		moves.add("left");		addMove();
		}
		board.move("down");		moves.add("down");		addMove();
	}

	/**
	 * Positions the empty cell into the desired place, directly under integer i
	 * First places the empty cell into the bottom row, and then places it under i accordingly
	 * @param i Integer for which the empty cell should be placed under
	 */
	public void PositionEmptyCell(int i) {
		
		//If empty cell is not in last row, keep going down
		while(board.getPositionOf(0) <= (numRow-1)*numCol - 1) {
			
			//If by chance the empty cell is under i during its process of going down, objective is achieved and stop
			if(board.getPositionOf(i) + numCol == board.getPositionOf(0)) {
				break;
			}
			board.move("down");		moves.add("down"); addMove();	
		}
		
		//If integer i is shuffled into place during the process of going down, move on to next integer
		if(board.getPositionOf(i) == i - 1) {
			return;
		}
		
		//while the empty cell is not under i, keep process going
		while(board.getPositionOf(0) != board.getPositionOf(i) + numCol) {
			
			//If empty cell is in last row, and i is directly left of it, sequence is needed
			if(board.getPositionOf(i) > (numRow-1)*numCol - 1 && board.getPositionOf(i) + 1 == board.getPositionOf(0)) {
				board.move("up");	moves.add("up"); addMove();
				board.move("left");	moves.add("left"); addMove();
				board.move("down");	moves.add("down"); addMove();
				break;
			}
			
			//if empty cell is in last row, and i is directly right of it, sequence is needed
			if(board.getPositionOf(i) > (numRow-1)*numCol - 1 && board.getPositionOf(i) - 1 == board.getPositionOf(0)) {
				board.move("up");	moves.add("up"); addMove();
				board.move("right");	moves.add("right"); addMove();
				board.move("down");	moves.add("down"); addMove();
				break;
			}
			
			//First moves the empty cell into the right column, if already in right column, move up
			if(board.getPositionOf(0)%numCol > board.getPositionOf(i)%numCol) {
				board.move("left");	moves.add("left"); addMove();
			}
			else if(board.getPositionOf(0)%numCol < board.getPositionOf(i)%numCol) {
				board.move("right"); moves.add("right"); addMove();
			}
			else {
				board.move("up"); moves.add("up"); addMove();
			}
		}
	}
	
	/**
	 * Algorithm for rotating the empty cell around the integer, in a way it brings it up
	 * @param i	Integer to be brought up
	 */
	public void BringIntegerUp(int i) {
				
		//1, 2 and 3 positioned in first row, 4, 5 ,6 ,7 in second row and 8 in the third row
			if(board.getPositionOf(i) > numCol - 1 + (numCol * RowsSolved)) {
				
				//If zero is under the integer, position it to the left of it
				if(board.getPositionOf(i) + numCol == board.getPositionOf(0)) {
					board.move("left"); moves.add("left"); addMove();
					board.move("up");	moves.add("up"); addMove();
				}
				
				//Other scenario is that it is already to the left of it
				board.move("up");		moves.add("up"); addMove();
				board.move("right");	moves.add("right"); addMove();
				board.move("down");		moves.add("down"); addMove();
				//empty cell ends directly under i
				
				//while i is not in desired place, keep rotating
				while(board.getPositionOf(i) > numCol - 1 + (numCol * RowsSolved)) {
					RotateUp();
				}
			}
		}
	
	/**
	 * Method for upwards rotation, if empty cell starts directly under the integer
	 */
	public void RotateUp() {
		board.move("left");	moves.add("left"); 	addMove();
		board.move("up");	moves.add("up"); 	addMove();
		board.move("up");	moves.add("up"); 	addMove();
		board.move("right");moves.add("down"); 	addMove();
		board.move("down"); moves.add("down");	addMove(); //0 ends below integer i
	}
	
	/**
	 * Algorithm for rotating the empty cell around the integer, in a way it brings it to the left
	 * @param i	Integer to be brought left
	 */
	public void BringIntegerLeft(int i) {
			
		if(board.getPositionOf(i) != i - 1) {
			
			//If zero is positioned left of the integer, position it to the right
			if(board.getPositionOf(0) + 1 == board.getPositionOf(i)) {
				board.move("right"); 	moves.add("right"); addMove();
			}
			//The other scenario is that the empty cell is directly under integer i, place it to the left
			else if(board.getPositionOf(i) + numCol == board.getPositionOf(0)) {
				board.move("left"); 	addMove();
				board.move("up");		addMove();
				board.move("right");	addMove();
			}
			
			//while i is not in correct place, keep rotating
			while(board.getPositionOf(i) != i - 1) {
				RotateLeft();
			}
		}
	}
	
	/**
	 *  Method for leftwards rotation, if empty cell is to the right of the integer
	 */
	public void RotateLeft() {
		board.move("down");		moves.add("down"); 	addMove();
		board.move("left");		moves.add("left"); 	addMove();
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up"); 	addMove();
		board.move("right");	moves.add("right"); addMove();
	}
			
	/**
	 * Algorithm for rotating the empty cell around the integer, in a way it brings to the right
	 * @param i	Integer to be brought to the right
	 */
	public void BringIntegerRight(int i) {
		
		//If integer i is not in the right most column, run the algorithm
		if((board.getPositionOf(i)+1)%numCol != 0 ){
		
			//If empty cell is directly under i, bring it to the left of it (is always the case)
			if(board.getPositionOf(i) + numCol == board.getPositionOf(0)) {
			
				board.move("right");	moves.add("right"); addMove();
				board.move("up");		moves.add("up");	addMove();
				board.move("left");		moves.add("left");	addMove();
			
				//While integer i is not in desired place, keep rotating
				while((board.getPositionOf(i)+1)%numCol != 0) {	
					RotateRight();
				}
			} 
		}
	}
	
	/**
	 *  Method for rightwards rotation, if empty cell is to the left of the integer
	 */
	public void RotateRight() {
		board.move("down");		moves.add("down");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("left");		moves.add("left");	addMove();
	}
	
	
	/**
	 * Sets up integer a and b in the bottom two rows in a way that one rotation puts them in place 
	 * and rotates a and b in place
	 * @param a	integer in the second-to-last row to be put in place
	 * @param b	integer in the last row to be put in place
	 */
	public void SetUp(int a, int b) {
		// a = 9, b = 13 in this first case for 4x4
		// Bring empty cell to bottom right corner
		while(board.getPositionOf(0) != numRow*numCol-1) {
			//if the empty cell is in the last column, move down
			if((board.getPositionOf(0) + 1)%numCol==0) {
				board.move("down");	moves.add("down"); addMove();
				}
			else {
			//Move to the right most column otherwise
			board.move("right");		moves.add("right");	addMove();
			}
		}
		
		//While b is not in desired place, rotate entire two rows until b is correctly placed
		while(board.getPositionOf(b) != (numRow-2)*numCol + ColumnsSolved) {
			Rotate(numCol - ColumnsSolved); //4 is the dimension for 4x4 puzzle
		}
		
		//If a is directly placed under b, special algorithm is needed to place it to the right of it
		if(board.getPositionOf(a) == board.getPositionOf(b) + numCol) { //Look out for case when 9 is right below 13
			
			FromUnderToRight(numCol - ColumnsSolved);
		}
		//If a is not placed under b, then rotate remaining numbers to the right of b until a is to the right of b
		else {
			while(board.getPositionOf(a) != (numRow-2)*numCol + 1 + ColumnsSolved) {
				Rotate(numCol - 1 - ColumnsSolved);
			}
		}
		//With a and b in desired place, rotate once through the bottom rows to put a and b in place
		Rotate(numCol-ColumnsSolved);
		ColumnsSolved++;
	} 
		
	/**
	 * If a is directly under b, special algorithm is needed to put a to the right of b
	 * @param steps number of steps the empty cell needs to take to reach a
	 */
	public void FromUnderToRight(int steps) {
		
		//Reach a that is directly under b
		for(int i = 0; i < steps - 1; i++) {
			board.move("left"); 	moves.add("left");	addMove();
		}
		
		//Rotate a away from b
		board.move("up");		moves.add("up");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("down");		moves.add("down");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("left");		moves.add("left");	addMove();
		
		//Rotate b to the left of a
		board.move("down");		moves.add("down");	addMove();
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("down");		moves.add("down"); 	addMove();
		
		//Rotate a and b into setup position
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up");	addMove();
		for(int i = 0; i < numCol - 1 - ColumnsSolved; i++) {
			board.move("right");	moves.add("right"); addMove();
		}
		board.move("down");	moves.add("down"); addMove();
	}
	
	/**
	 * Method for rotating through the last two rows
	 * @param dimension, keeps track of how many steps to the right or left we can make, 
	 * 					 without disturbing the solved columns
	 */
	public void Rotate(int dimension) 
	{
		//Go to the left
		for(int i = 1; i < dimension ; i++) {
			board.move("left"); moves.add("left");	addMove();
		}
		//Move one up
		board.move("up"); moves.add("up");	addMove();
		
		//Go to the right
		for(int i = 1; i < dimension; i++) {
			board.move("right"); moves.add("right");	addMove();
		}
		//Move one down
		board.move("down"); moves.add("down");	addMove();
	}

	/**
	 * Method for rotating the final 2x2 squares into place, starts with empty cell on bottom right corner
	 */
	public void RotateFinal() {
		
		//If the puzzle is solvable, then if 0 and 15 are in the right index, the other two should be right as well
		while(board.getPositionOf(0) != numRow*numCol-1 || board.getPositionOf(numRow*numCol-1) != numRow*numCol-2) {
			//Rotate through 2x2 square
			board.move("left");		moves.add("left"); addMove();
			board.move("up");		moves.add("up");	 addMove();
			board.move("right");	moves.add("right"); addMove();
			board.move("down");		moves.add("down"); addMove();
			}
		}
	}	
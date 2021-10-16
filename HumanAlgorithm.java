import java.util.ArrayList;

public class HumanAlgorithm {

	private GameBoard board;
	private ArrayList<String> moves;
	private ArrayList<ArrayList<Integer>> movesPuzzle = new ArrayList<ArrayList<Integer>>(); //ADDED BY KES
	private int RowsSolved;
	private int ColumnsSolved;
	private ArrayList<Integer> start = new ArrayList<Integer>();

	public static void main(String args[]) {
		
		//Test algorithm
		//for(int i = 0; i < 10000; i++) {
		Puzzle myPuzzle = new Puzzle();
		System.out.println("Starting " + myPuzzle.getBoard());
		
		GameBoard game = new GameBoard(myPuzzle.getBoard());
		HumanAlgorithm person = new HumanAlgorithm(game);
		person.solve();
	
		//}
	}
	
	public HumanAlgorithm(GameBoard start) {
		this.board = start;
		moves = new ArrayList<String>();
		this.start = start.getBoard();
		RowsSolved = 0;
		ColumnsSolved = 0;
		addMove(); //ADDED BY KES
	}
	
	public  void solve() { 

		//First solve for first two rows
		for(int i = 1; i <= 8; i++) {
			
			if(i%4==0) {
				RowsSolved++;
			}
			if(board.getPositionOf(i) != i - 1) {
				PositionEmptyCell(i);
				
				if(board.getPositionOf(i) == i - 1)
				{
		
					continue;
				}
				
				if(i%4 == 0) {
	
					
					//If the integer is the last integer of the row, another combination is needed
					BringIntegerRight(i);
					BringIntegerUp(i);
					BringLastIntegerOfRow(i);
					
					continue;
				}
				
				//Bring Integer to the right, then up, then slide it to the left to place it 
				//without disturbing other integers already in place
				BringIntegerRight(i);
				BringIntegerUp(i);
				BringIntegerLeft(i);
			}
		}
		
		//Solve for bottom two rows
		for(int i = 9; i < 11; i++) {
			
			SetUp(i, i + 4);
		}
		
		//solve last 4 squares
		RotateFinal();
		
		System.out.println("moves" + moves); ///ADDED THE WORDS MOVES TO PRINT!!!!!!!!
		for (int i = 0; i < movesPuzzle.size(); i++) {
			System.out.println(movesPuzzle.get(i));
		}
		System.out.println(moves.size());
		System.out.println("Final state: " + this.start);
	}
	
	//METHOD BY KES
	public void addMove() {
		ArrayList<Integer> sub = new ArrayList<Integer>(board.getBoard());
		movesPuzzle.add(sub);
	}
	
	public ArrayList<ArrayList<Integer>> getMoves(){
		return movesPuzzle;
	}
	
		
	//Algorithm for bringing the last integer of the row into the row, with the integer
	//positioned under its destined square, and 0 being either left or under it
	// ADDED ALL THE movesPuzzle (ADDED BY KES)
	public void BringLastIntegerOfRow(int i) {
		
		if(board.getPositionOf(i) + 4 == board.getPositionOf(0)) {
			board.move("left");	moves.add("left"); 	addMove();
			board.move("up");	moves.add("up"); 	addMove();
		}
		board.move("left");		moves.add("left");		addMove();	
		board.move("left");		moves.add("left");		addMove();
		board.move("up");		moves.add("up");		addMove();
		board.move("right");	moves.add("right");		addMove();
		board.move("right"); 	moves.add("right");		addMove();
		board.move("right"); 	moves.add("right");		addMove();
		board.move("down");		moves.add("down");		addMove();
		board.move("left");		moves.add("left");		addMove();
		board.move("up");		moves.add("up");		addMove();
		board.move("left");		moves.add("left");		addMove();
		board.move("left");		moves.add("left");		addMove();
		board.move("down");		moves.add("down");		addMove();
	}
	
	//Places the empty cell underneath or to the left of the wanted integer
	//KES ADDED ALL THE movesPuzzle.add
	public void PositionEmptyCell(int i) {
		
		while(board.getPositionOf(0) != 15) {
			
			if(board.getPositionOf(0) > 11) {
				board.move("right");	moves.add("right");	addMove();
				}
			else {
			board.move("down");		moves.add("down"); addMove();
			}
		}
		
		if(board.getPositionOf(i) == i - 1) {
			return;
		}
		
		while(board.getPositionOf(0) > board.getPositionOf(i)) {
			
			if(board.getPositionOf(i) > 11 && board.getPositionOf(i) + 1 == board.getPositionOf(0)) {
				board.move("up");	moves.add("up"); addMove();
				board.move("left");	moves.add("left"); addMove();
				board.move("down");	moves.add("down"); addMove();
			}
			
			if(board.getPositionOf(i) + 4 == board.getPositionOf(0)) {
				break;
			}
			
			if(board.getPositionOf(0)%4 > board.getPositionOf(i)%4) {
				board.move("left");	moves.add("left"); addMove();
			}
			else if(board.getPositionOf(0)%4 < board.getPositionOf(i)%4) {
				board.move("right"); moves.add("right"); addMove();
			}
			else {
				board.move("up"); moves.add("up"); addMove();
			}
		}
	}
	
	//Algorithm for bringing an integer up
	//KES ADDED ALL THE movesPuzzle
	public void BringIntegerUp(int i) {
				
		//1, 2 and 3 belong to first row, 4, 5 ,6 ,7 to second row and 8 in the third row
				
			if(board.getPositionOf(i) > 3 + (4 * RowsSolved)) {
				
				//If zero is under the integer, position it to the left of it
				if(board.getPositionOf(i) + 4 == board.getPositionOf(0)) {
					board.move("left"); moves.add("left"); addMove();
					board.move("up");	moves.add("up"); addMove();
				}
				
				//Other scenario is that it is already to the left of it
				board.move("up");		moves.add("up"); addMove();
				board.move("right");	moves.add("right"); addMove();
				board.move("down");		moves.add("down"); addMove();
				
				while(board.getPositionOf(i) > 3 + (4 * RowsSolved)) {
					RotateUp();
				}
			}
		}
	
	//Automate algorithm for rotating it up
	//KES ADDED ALL THE movespuzzle.add
	public void RotateUp() {
		board.move("left");	moves.add("left"); 	addMove();
		board.move("up");	moves.add("up"); 	addMove();
		board.move("up");	moves.add("up"); 	addMove();
		board.move("right");moves.add("down"); 	addMove();
		board.move("down"); moves.add("down");	addMove(); //0 ends below integer i
	}
	
	//Algorithm for bringing an integer to the left
	//KES ADDED ALL THE movespuzzle.add
	public void BringIntegerLeft(int i) {
			
		if(board.getPositionOf(i) != i - 1) {
			
			//If zero is positioned left of the integer, position it to the right
			if(board.getPositionOf(0) + 1 == board.getPositionOf(i)) {
				board.move("right"); 	moves.add("right"); addMove();
			}
			else if(board.getPositionOf(i) + 4 == board.getPositionOf(0)) {
				board.move("left"); 	addMove();
				board.move("up");		addMove();
				board.move("right");	addMove();
			}
			
			while(board.getPositionOf(i) != i - 1) {
				RotateLeft();
			}
		}
	}
	
	//Automate rotation to the left
	//KES ADDED ALL THE movesPuzzle
	public void RotateLeft() {
		board.move("down");		moves.add("down"); 	addMove();
		board.move("left");		moves.add("left"); 	addMove();
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up"); 	addMove();
		board.move("right");	moves.add("right"); addMove();
	}
			
	//Algorithm for bringing an integer to the right
	//KES ADDED ALL THE movesPuzzzle.add
	public void BringIntegerRight(int i) {
		
		//If zero is positioned underneath, bring it to the left
	if((board.getPositionOf(i)+1)%4 != 0 ){
		
		if(board.getPositionOf(i) + 4 == board.getPositionOf(0)) {
			
			board.move("right");	moves.add("right"); addMove();
			board.move("up");		moves.add("up");	addMove();
			board.move("left");		moves.add("left");	addMove();
			
			while((board.getPositionOf(i)+1)%4 != 0) {	
				RotateRight();
			}
			//Only other option is that zero is positioned to the right
		}   else if(board.getPositionOf(i) + 1 == board.getPositionOf(0)) {
			board.move("left");		moves.add("left"); addMove();
			
			while((board.getPositionOf(i)+1)%4 != 0) {
				RotateRight(); 
			}	
		}
	}
	}
	
	//Automate algorithm for rotating to the right
	//KES ADDED ALL THE movesPuzzle
	public void RotateRight() {
		board.move("down");		moves.add("down");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("left");		moves.add("left");	addMove();
	}
	
	
	//For bottom two rows, set up the integers in a way so that the column can be slided in
	//KES ADDED ALL THE movesPuzzle
	public void SetUp(int a, int b) {
		// a = 9, b = 13 in this first case
		//Bring empty cell to bottom right corner
		while(board.getPositionOf(0) != 15) {
			if(board.getPositionOf(0) > 11) {
				board.move("right");	moves.add("right"); addMove();
				}
			else {
			board.move("down");		moves.add("down");	addMove();
			}
		}
		
		//While b is misplaced, rotate entire two rows until b is correctly placed
		while(board.getPositionOf(b) != 8 + ColumnsSolved) {
			Rotate(4 - ColumnsSolved); //4 is the dimension
		}
		
		//If a is directly placed under b, special algorithm is needed to place it to the right of it
		if(board.getPositionOf(a) == board.getPositionOf(b) + 4) { //Look out for case when 9 is right below 13
			
			FromUnderToRight(4 - ColumnsSolved);
		}
		//If a is not placed under b, then rotate remaining numbers to the right of b until a is to the right of b
		else {
			
			while(board.getPositionOf(a) != 9 + ColumnsSolved) {
				
				Rotate(3 - ColumnsSolved);
			}
		}
		Rotate(4-ColumnsSolved);
		ColumnsSolved++;
	} 
		
	//Separate algorithm for moving a from under b to the right of b
	//KES ADDED ALL THE movesPuzzle.add
	public void FromUnderToRight(int steps) {
		
		for(int i = 0; i < steps - 1; i++) {
			board.move("left"); 	moves.add("left");	addMove();
		}
		
		board.move("up");		moves.add("up");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("down");		moves.add("down");	addMove();
		board.move("right");	moves.add("right");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("left");		moves.add("left");	addMove();
		
		board.move("down");		moves.add("down");	addMove();
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up");	addMove();
		board.move("right");	moves.add("right");	addMove();
		
		board.move("down");		moves.add("down"); 	addMove();
		board.move("left");		moves.add("left");	addMove();
		board.move("up");		moves.add("up");	addMove();
		
		for(int i = 0; i < 3 - ColumnsSolved; i++) {
			board.move("right");	moves.add("right"); addMove();
		}
		board.move("down");	moves.add("down"); addMove();
	}
	
	//Automate algorithm for rotation
	//KES ADDED ALL THE movePuzzle.add
	public void Rotate(int dimension) 
	{
		for(int i = 1; i < dimension ; i++) {
			board.move("left"); moves.add("left");	addMove();
		}
		board.move("up"); moves.add("up");
		for(int i = 1; i < dimension; i++) {
			board.move("right"); moves.add("right");	addMove();
		}
		board.move("down"); moves.add("down");	addMove();
	}

	public void RotateFinal() {
		
		board.move("down");		addMove();
		//If the puzzle is solvable, then if 0 and 15 are in the right index, the other two should be right as well
		while(board.getPositionOf(0) != 15 || board.getPositionOf(15) != 14) {
			board.move("left");		addMove();
			board.move("up");		addMove();
			board.move("right");	addMove();
			board.move("down");		addMove();

			}
		}
	}	

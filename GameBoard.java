import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
/**
 * This is the object for gameboard which creates the game, doublechecks if it is solvable (just in case someone modifies
 * the code for a user created starting position) and then provides different methods to help coders
 * implement gmaes with it such as get height, move, etc
 * @author Philip Palim, Kes Visser, Liu Nuo Su, Liz Thompson, Felix Van Den Heijkant
 *
 */
public class GameBoard {
	private ArrayList<Integer> listLocation = new ArrayList<Integer>();
	private int locationOfZero;
	private int rowLength;
	private int listLength;


	/**
	 *takes a list of integers where the integer represents the number of the piece, and the place of that integer in the array list represents where it is on the board.
	 *This object can accept any list of an integer squared length.  For example a list of sixteen would equate to a 4x4 with place 0 being (1,10 and place 15 being (4,4)
	 */
	public GameBoard(ArrayList<Integer> gameBoard)  {
		listLocation=gameBoard;
		locationOfZero=listLocation.indexOf(0);
		listLength=listLocation.size();
		//the square root of the length will give us the number of rows and columns
		rowLength=(int) (Math.sqrt(listLength));
		if(solvable(listLocation)){
			System.out.println("unsolvable game configuration");
		}

	}
	
	/**
	 * a method which returns the height(width) of the gameBoard
	 * @return the number of rows/columns
	 */
	public int getHeight(){
		return rowLength;
	}
	
	
	/**
	 * this method checks whether or not the gameboard is complete
	 * @return a boolean which indicates whether or not the game is successfully completed
	 */
	public final boolean checkIfComplete(){
		boolean isComplete=false;
		ArrayList<Integer> perfectList = new ArrayList<Integer>();
		perfectList.add(1);
		perfectList.add(2);
		perfectList.add(3);
		perfectList.add(4);
		perfectList.add(5);
		perfectList.add(6);
		perfectList.add(7);
		perfectList.add(8);
		perfectList.add(9);
		perfectList.add(10);
		perfectList.add(11);
		perfectList.add(12);
		perfectList.add(13);
		perfectList.add(14);
		perfectList.add(15);
		perfectList.add(0);
		if(listLocation.equals(perfectList)){
			isComplete=true;
		}
		return isComplete;
	}
	
	/**
	 * this returns the current gameboard in the form of the arraylist
	 * @return the current gameboard list
	 */
	public final ArrayList<Integer> getBoard(){
		return listLocation;
	}

	/**
	 * change the board given the moves that people make.  Moves must be "left" "right" "up" or "down"
	 * @param direction tells the code where you want to move
	 */
	public final void move(String direction){
		//checks if the piece can move up, and if so, switches it with the appropriate spot
		if((direction=="up")&&(locationOfZero-rowLength>=0)){
			this.listLocation.set(locationOfZero, listLocation.get(locationOfZero-rowLength));
			this.listLocation.set(locationOfZero-4, 0);
		}
		
		
		//we can also do this with a swap but it didn't work either
		//checks if the piece can move down, and if so, switches it with the appropriate spot
		if((direction=="down")&&(locationOfZero+rowLength<=listLength-1)){
			this.listLocation.set(locationOfZero, listLocation.get(locationOfZero+rowLength));
			this.listLocation.set(locationOfZero+rowLength, 0);

		}
		
		
		//checks if the piece can move right by ensuring that it is not the last in a row, and if so, switches it with the appropriate spot
		if((direction=="right")&&(((locationOfZero+1)%4)!=0)){
			this.listLocation.set(locationOfZero, listLocation.get(locationOfZero+1));
			this.listLocation.set(locationOfZero+1, 0);

		}
		
		
		// checks if the piece can move left by making sure it is not the first in a row, and if it can, we switch the appropriate pieces
		if((direction=="left")&&(((locationOfZero)%4)!=0)){
			this.listLocation.set(locationOfZero, listLocation.get(locationOfZero-1));
			this.listLocation.set(locationOfZero-1, 0);

		}
		locationOfZero=listLocation.indexOf(0);
	}


	/**
	 * Method from the class solvable. Checks if the puzzle/game is solvable.
	 * @param input the configuration of the gameboard for which we are checking whether or not it is solvable
	 * @return a boolean telling us whether or not it is solvable
	 */
	public static boolean solvable(ArrayList<Integer> input) {
		boolean solvable = false;
		int inversion = 0;
		int zeroRow = 0; //the row that holds the zero
		int zeroRowFromBottom = 0;
		int rowLength = (int) (Math.sqrt(input.size()));
		
		//Calculate the number of inversions
		for (int i = 0; i < input.size(); i++) {
			for (int j = i+1; j < input.size(); j++) {
				if (input.get(i) > input.get(j) && input.get(j) > 0 && input.get(i) > 0) {
					inversion++;
				}
			}
		}
		
		//Find the row that holds the zero
		for (int i = 0; i<input.size(); i++) {
			if (input.get(i) == 0 && i%4 != 0) {
				zeroRow = (int) Math.ceil(i / 4.0);
			}
			else if (input.get(i) == 0 && i%4 == 0) {
				zeroRow = (int) Math.ceil(i/4.0) + 1;
			}
		}
		
		//Calculate the row from the bottom that holds the zero
		zeroRowFromBottom = 1+rowLength-zeroRow;
		
		//Check if it is solvable
		if(zeroRowFromBottom%2 == 0 && inversion%2 !=0) {
			solvable = true;
		}
		else if (zeroRowFromBottom%2 != 0 && inversion%2 == 0) { 
			solvable = true;
		}
		
		return solvable;
		}
	/**
	 * This prints out the puzzle 
	 */
	public void printPuzzle() {
    	int k = 0;
    	for(int i = 0; i < this.getHeight(); i++) {
    		for(int j = 0; j < this.getHeight(); j++) {
    			if(this.getBoard().get(k) == 0) {
    				System.out.print("   ");
    			} else if(this.getBoard().get(k) < 10) {
    			System.out.print(this.getBoard().get(k) + "  ");
    			} else {
    				System.out.print(this.getBoard().get(k) + " ");
    			}
    			k++;
    		}
    		System.out.println("");
    	}
    }
	
}

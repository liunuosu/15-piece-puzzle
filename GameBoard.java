import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class GameBoard {
	private ArrayList<Integer> listLocation = new ArrayList<Integer>();
	private int locationOfZero;
	private int rowLength;
	private int listLength;


	//takes a list of integers where the integer represents the number of the piece, and the place of that integer in the array list represents where it is on the board.
	//This object can accept any list of an integer squared length.  For example a list of sixteen would equate to a 4x4 with place 0 being (1,10 and place 15 being (4,4)
	public GameBoard(ArrayList<Integer> gameBoard)  {
		listLocation=gameBoard;
		locationOfZero=listLocation.indexOf(0);
		listLength=listLocation.size();
		//the square root of the length will give us the number of rows and columns
		rowLength=(int) (Math.sqrt(listLength));
		if(!solvable(listLocation)){
			System.out.println("unsolvable game configuration");
		}

	}
	
	
	//get width is pointless since it's always the same as height
	public int getHeight(){
		return rowLength;
	}
	
	public int getLocationOfZero() {
		return this.locationOfZero;
	}
	
	//this is the check if complete method
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
		if(!listLocation.equals(perfectList)){
			isComplete=true;
		}
		return isComplete;
	}
	
	
	//returns the board as it currently is
	public final ArrayList<Integer> getBoard(){
		return this.listLocation;
	}

	
	//will change the board given the moves that people make.  Moves must be "left" "right" "up" or "down"
	public void move(String direction){
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
	
	//Method from Liu's/Philip's gameboard
	public int getPositionOf(int number) {
		return listLocation.indexOf(number);
	}


	//Method from the class solvable. Checks if the puzzle/game is solvable.
	public static boolean solvable(ArrayList<Integer> input) {
		boolean solvable = false;
		int inversion = 0;
		int row = 0; //row in which the 0 is
		int rowFromBottom = 0;

		for (int i = 0; i < input.size(); i++) {
			for (int j = i+1; j < input.size(); j++) {
				if (input.get(i) > input.get(j) && input.get(j) > 0 && input.get(i) > 0) {
					inversion++;
				}
			}
		}

		for (int i = 0; i<input.size(); i++) {
			if (input.get(i) == 0 && i%4 != 0) {
				row = (int) Math.ceil(i / 4.0);
			}
			else if (input.get(i) == 0 && i%4 == 0) {
				row = (int) Math.ceil(i/4.0) + 1;
			}
		}

		rowFromBottom = 4 - row + 1;

		if(rowFromBottom%2 == 0 && inversion%2 !=0) { //rowFromBottom is even
			solvable = true;
		}
		else if (rowFromBottom%2 != 0 && inversion%2 == 0) { 
			solvable = true;
		}

		return solvable;
	}
	
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



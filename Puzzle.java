import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This class makes a solvable random puzzle.
 * @author Kes Visser 524858kv
 */

public class Puzzle {
	
	private ArrayList<Integer> thePuzzle = new ArrayList<Integer>();
	
	/**
	 * This method is the constructor for the class Puzzle.
	 * It will shuffle a arrayList with numbers 0 to 16 until the arrayList holds a puzzle which you can solve in a 15-puzzle.
	 * This constructor uses the method solvable to check if an arrayList has a solvable puzzle.
	 */
	public Puzzle() {
		Integer[] numbersAvailable = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		boolean solvable = false;
		ArrayList<Integer> game = new ArrayList<>(Arrays.asList(numbersAvailable));
		
		while (!solvable) {
			Collections.shuffle(game);
			if (solvable(game)) {
				solvable = true;
			}
		}
		this.thePuzzle = game;
		
	}
	
	/**
	 * This method returns the arraylist that holds the puzzle.
	 * @return an arraylist containing the numbers 0 to 16
	 */
	public final ArrayList<Integer> getPuzzle(){
		return thePuzzle;
	}
	
	//Method from the class solvable. Checks if the puzzle/game is solvable.
	//Write case for when it is finished
	/**
	 * This method determines if 'input' is solvable. It does this by calculating the row from the bottom that holds the element 0 and
	 * by calculating the number of inversion. And inversion is when input[i]>input[i+j] for j>0, for example (1,3,2) then (3,2) is a inversion.
	 * We know that a 15 puzzle is solvable if inversion is odd and row from the bottom with the 0 is even. It is also solvable when
	 * inversion is even and row from the bottom with the 0 is odd.
	 * @param input an arrayList with numbers 0 to 16 for which we wish to know if that arrayList will make a solvable 15-puzzle.
	 * @return returns true if input can be solved with a 15-puzzle, returns false if it can not be solved with a 15-puzzle.
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
	
}

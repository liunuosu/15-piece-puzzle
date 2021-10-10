import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Puzzle {
	private ArrayList<Integer> thePuzzle = new ArrayList<Integer>();
	
	public static void main(String[] args) {
		Puzzle myPuzzle = new Puzzle();
		System.out.println(myPuzzle.getBoard());
	}
	
	//Makes a random puzzle/game, uses class solvable
	public Puzzle() {
		Integer[] numbersAvailable2 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		int length = numbersAvailable2.length;
		boolean solvable = false;
		 
		ArrayList<Integer> game = new ArrayList<>();
		
		while (!solvable) {
			for (int i = 0; i < length; i++) {
				ArrayList<Integer> numbersAvailable = new ArrayList<>(Arrays.asList(numbersAvailable2));
				Random random = new Random();
				int x = random.nextInt(numbersAvailable.size());
				game.add(numbersAvailable.get(x));
				numbersAvailable.remove(x);
			}
			if (solvable(game)) {
				solvable = true;
			}
			else {
				game.clear();
			}
		}
		
		this.thePuzzle = game;
		
	}
	
	//get method from GameBoard, from philips class
	public final ArrayList<Integer> getBoard(){
		return thePuzzle;
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
		
		rowFromBottom = 4-row+1;
		
		if(rowFromBottom%2 == 0 && inversion%2 !=0) { //rowFromBottom is even
			solvable = true;
		}
		else if (rowFromBottom%2 != 0 && inversion%2 == 0) { 
			solvable = true;
		}
		
		return solvable;
	}
	
}

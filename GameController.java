import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * 
 * @author 
 *
 */
public class GameController {
	
	//Global Variables
	private GameBoard game;
	private ArrayList<Integer> puzzle = new ArrayList<Integer>();
	private String solver;
	private int numberOfSteps;
	private ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
	
	
	/**
	 * 
	 * @param inital
	 */
	// change solve it in different method
	public GameController(GameBoard inital, String solver) {
		game = inital;
		puzzle = inital.getBoard();
		this.solver = solver;
		numberOfSteps = 0;
		//moves = solver();
	}
	
	/**
	 * 
	 * @return
	 */
	public GameBoard getBoard() {
		return game;
	}
	
	/**
	 * 
	 */
	public void createNewGame() {
		//System.out.println("createNewGame");
		Puzzle newPuzzle = new Puzzle();
		
		puzzle.clear();
		//ArrayList<Integer> newPuzzle2 = new ArrayList<>((newPuzzle.getBoard()));
		this.puzzle = newPuzzle.getPuzzle();
		game.setPuzzle(puzzle); //problem
		this.numberOfSteps = 0;
		//moves = solver();
		//System.out.println("createNewGame2");
	}
	
	public void setSolver(String solver) {
		this.solver = solver;
	}
	
	public int numberOfSteps() {
		return moves.size();
	}
	
	//Make a solver class and return moves
	public void solver(){
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		if (solver.equals("A* solver")) {
			Node initialNode = new Node(game.getBoard(),0);
			A_star a = new A_star(initialNode);
			ArrayList<Integer> kes = new ArrayList<Integer>();
			kes = a.getPathSet().get(2).getBoard();
			moves.add(kes);
			for (int i=a.pathSet.size()-1; i>=0; i--) {
				//ArrayList<Integer> kes = new ArrayList<Integer>();
				//kes = a.getPathSet().get(i).getBoard();
				//moves.add(kes);
			}
		}
		else {
			HumanAlgorithm person = new HumanAlgorithm(game);
			person.solve();
			moves = person.getMoves();
		}
		//System.out.println(moves);
		this.moves = moves;
	}
	
	/**
	 * 
	 */
	//for A_star pathSet contains all the moves
	//for might mess with the solvers if we use GameBoard, if it does just introduce private puzzle and coppy it in there.
	public void moveTile() {
		
		//game.setPuzzle(moves.get(numberOfMoves));
		if (game.getBoard().equals(moves.get(1))) {
			//game.setPuzzle(moves.get(3));
		}
		else {
			//game.setPuzzle(moves.get(1));
		}
		game.setPuzzle(moves.get(numberOfSteps));
		numberOfSteps++;
		//System.out.println(numberOfMoves);
		;
		
	}
	
	/**
	 * 
	 */
	//Maybe we have to call a new gamecontroller? Or wipe moves but no way to get new ones unless we solve the things again
	public void clearBoard() {
		Integer[] finishedArray = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
		ArrayList<Integer> finishedPuzzle = new ArrayList<>(Arrays.asList(finishedArray));
		GameBoard finishedGame = new GameBoard(finishedPuzzle);
		this.puzzle = finishedPuzzle;
		this.game = finishedGame;
		
	}
	

}

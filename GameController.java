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
	private int numberOfMoves;
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
		numberOfMoves = 0;
		moves = solver();
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
		System.out.println("createNewGame");
		Puzzle newPuzzle = new Puzzle();
		
		puzzle.clear();
		//ArrayList<Integer> newPuzzle2 = new ArrayList<>((newPuzzle.getBoard()));
		this.puzzle = newPuzzle.getBoard();
		game.setPuzzle(puzzle); //problem
		System.out.println(game.getBoard());
		this.numberOfMoves = 0;
		moves = solver();
		System.out.println("createNewGame2");
	}
	
	public void setSolver(String solver) {
		this.solver = solver;
	}
	
	//Make a solver class and return moves
	public ArrayList<ArrayList<Integer>> solver(){
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		if (solver.equals("A-star")) {
			Node initialNode = new Node(game.getBoard(),0);
			A_star a = new A_star(initialNode);
			for (int i=0; i<a.pathSet.size(); i++) {
				moves.add(a.getPathSet().get(a.pathSet.size()-i-1).getBoard());
			}
		}
		else {
			HumanAlgorithm person = new HumanAlgorithm(game);
			person.solve();
			moves = person.getMoves();
		}
		return moves;
	}
	
	/**
	 * 
	 */
	//for A_star pathSet contains all the moves
	//for might mess with the solvers if we use GameBoard, if it does just introduce private puzzle and coppy it in there.
	public void moveTile() {
		this.puzzle = moves.get(numberOfMoves);
		numberOfMoves++;
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

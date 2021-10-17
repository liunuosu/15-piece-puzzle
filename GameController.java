import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that controls and performs simulation steps.
 * @author Philip Palim, Kes Visser, Liu Nuo Su, Liz Thompson, Felix Van Den Heijkant
 *
 */
public class GameController {
	
	//Global Variables
	private GameBoard game;
	private ArrayList<Integer> puzzle = new ArrayList<Integer>();
	private String solver;
	private int numberOfSteps; //number of steps we have made to solve the puzzle
	private ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
	
	/**
	 * Constructor which creates a simulation based on a current game board and a solver.
	 * @param inital The game board to perform a simulation with.
	 */
	public GameController(GameBoard inital) {
		this.game = inital;
		this.puzzle = inital.getBoard();
		this.numberOfSteps = 0;
	}
	
	/**
	 * Getter for obtaining the current 15-puzzle from the game board.
	 * @return an arrayList that holds integers 0 to 16, which represent our puzzle.
	 */
	public GameBoard getBoard() {
		return game;
	}
	
	/**
	 * Creates a new 15-puzzle and change the puzzle in game board to this new puzzle. It also resets the numberOfSteps to 0.
	 */
	public void createNewGame() {
		puzzle.clear();
		this.numberOfSteps = 0;
		Puzzle newPuzzle = new Puzzle();
		this.puzzle = newPuzzle.getPuzzle();
		game.setPuzzle(puzzle);
	}
	
	/**
	 * Sets the solver to the user specified type. This can be A* method or the HumanAlgorithm.
	 * @param solver a String that says A* or HumanAlgorithm.
	 */
	public void setSolver(String solver) {
		this.solver = solver;
	}
	
	/**
	 * This method solves the puzzle and then puts all the moves needed to get there in 'moves'.
	 * The method checks what type of solver the user wants to use and then calls this class to solve the puzzle. Then it will add all these
	 * moves equal to 'moves'. A move is a arrayList where 2 numbers from the previous puzzle have switched. See the solvers to see what move
	 * has been made or why.
	 */
	public void solver(){
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
		
		if (solver.equals("A* solver")) {
			Node initialNode = new Node(game.getBoard(),0);
			A_star a = new A_star(initialNode);
			ArrayList<Integer> kes = new ArrayList<Integer>();
			kes = a.getPathSet().get(2).getBoard();
			moves.add(kes);
			for (int i=a.pathSet.size()-1; i>=0; i--) {
				ArrayList<Integer> sub = new ArrayList<Integer>();
				sub = a.getPathSet().get(i).getBoard();
				moves.add(sub);
			}
		}
		
		else {
			HumanAlgorithm person = new HumanAlgorithm(game);
			person.solve();
			moves = person.getMoves();
		}
		this.moves = moves;
	}
	
	/**
	 * Method that returns the number of steps need to go from our puzzle to the solved puzzle.
	 * @return the number of steps needed to get to the solved puzzle.
	 */
	public int numberOfTotalSteps() {
		return moves.size();
	}
	
	/**
	 * Moves a tile in the game. It goes into moves and gets the next puzzle. So the next step according to our solver.
	 * After having found our new puzzle it sets the puzzle in our game board to this puzzle and it updates number of steps.
	 */
	public void moveTile() {
		game.setPuzzle(moves.get(numberOfSteps));
		numberOfSteps++;
	}

}

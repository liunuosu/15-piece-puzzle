import java.util.ArrayList;

/**
 * Class that controls and performs simulation steps.
 * 
 * @author Kes Visser 524858kv
 *
 */
public class GameController {

	// Global Variables
	private GameBoard game;
	private ArrayList<Integer> puzzle = new ArrayList<Integer>();
	private String solver;
	private int numberOfSteps; // number of steps we have made to solve the puzzle
	private ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

	/**
	 * Constructor which creates a simulation based on a current game board and a
	 * solver.
	 * 
	 * @param inital The game board to perform a simulation with.
	 * @param solver the solver with which we which to solve the 15-puzzle.
	 */
	public GameController(GameBoard inital, String solver) {
		this.game = inital;
		this.puzzle = inital.getBoard();
		this.solver = solver;
		this.numberOfSteps = 0;
	}

	/**
	 * Getter for obtaining the current 15-puzzle from the game board.
	 * 
	 * @return an arrayList that holds integers 0 to 16, which represent our puzzle.
	 */
	public GameBoard getBoard() {
		return game;
	}

	/**
	 * Gets the solved state of a puzzle. That is in ordered form (1-15)
	 * 
	 * @return a GameBoard with the solved configuration of the puzzle
	 */
	public GameBoard getSolvedBoard() {
		GameBoard solved = new GameBoard(moves.get(numberOfTotalSteps() - 1));
		return solved;
	}

	/**
	 * Creates a new 15-puzzle and change the puzzle in game board to this new
	 * puzzle. It also resets the numberOfSteps to 0.
	 */
	public void createNewGame() {
		puzzle.clear();
		this.numberOfSteps = 0;
		Puzzle newPuzzle = new Puzzle();
		this.puzzle = newPuzzle.getPuzzle();
		game.setPuzzle(puzzle);
	}

	/**
	 * Sets the solver to the user specified type. This can be A* method or the
	 * HumanAlgorithm.
	 * 
	 * @param solver a String that says A* or HumanAlgorithm.
	 */
	public void setSolver(String solver) {
		this.solver = solver;
	}

	/**
	 * This method solves the puzzle and then puts all the moves needed to get there
	 * in 'moves'. The method checks what type of solver the user wants to use and
	 * then calls this class to solve the puzzle. Then it will add all these moves
	 * equal to 'moves'. A move is a arrayList where 2 numbers from the previous
	 * puzzle have switched. See the solvers to see what move has been made or why.
	 */
	public void solver() {
		ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

		if (solver.equals("A* solver")) {
			Node initialNode = new Node(game.getBoard(), 0);
			A_star a = new A_star(initialNode);
			ArrayList<Integer> kes = new ArrayList<Integer>();
			kes = a.getPathSet().get(2).getBoard();
			moves.add(kes);
			for (int i = a.pathSet.size() - 1; i >= 0; i--) {
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
	 * Method that returns the number of steps need to go from our puzzle to the
	 * solved puzzle.
	 * 
	 * @return the number of steps needed to get to the solved puzzle.
	 */
	public int numberOfTotalSteps() {
		return moves.size();
	}

	/**
	 * Moves a tile in the game. It goes into moves and gets the next puzzle. So the
	 * next step according to our solver. After having found our new puzzle it sets
	 * the puzzle in our game board to this puzzle and it updates number of steps.
	 */
	public void moveTile() {
		game.setPuzzle(moves.get(numberOfSteps));
		numberOfSteps++;
	}

}

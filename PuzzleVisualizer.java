
/**
 * This class allows us to create a window and see the solution of the 15-puzzle
 * game. There are two solvers available: A* algorithm and Human Algorithm.
 * 
 * Solver: choose a solver by pressing it's buttons
 * 
 * Show Solution: plays an animation of the step-by-step solution, you can also
 * stop the animation
 * 
 * Next Step: shows next move in the solution
 * 
 * Final Result: shows the solved game and corresponding number of steps to
 * solution
 * 
 * New Game: creates a new random puzzle Have fun!
 *
 */
public class PuzzleVisualizer {

	public static void main(String[] args) {
		// creates a new random and solvable puzzle
		Puzzle puzzle = new Puzzle();
		// creates a game board and controller for puzzle
		GameBoard game = new GameBoard(puzzle.getPuzzle());
		GameController control = new GameController(game);
		// creates a window for visualizing the game
		PuzzleWindow visualizer = new PuzzleWindow(control);
		visualizer.setVisible(true);

	}

}

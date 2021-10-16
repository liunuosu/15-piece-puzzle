import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;

/**
 * Allows user to visualize 15-Puzzle simulation
 * @author liztr
 *
 */
public class PuzzleWindow extends JFrame {
	
	private static final long serialVersionUID = -6629882116604217240L;
	//panel for visualizing the game
	private PuzzlePanel boardPanel;
	//game to be simulated
	private GameController control;
	
	//Interface Components
	//button for starting new game
	private JButton newGame;
	//spinner for selecting solver to be used
	private JSpinner solverType;
	//spinner for showing step by step solution
	private JSpinner showSolution;
	//spinner for showing number of iterations
	private JSpinner showIterations;
	
	// If an animation is active it is performed by this Thread
	private Thread running;
	
	
	/**
	 * Creates a window for visualizing the desired game 
	 * @param game
	 */
	public PuzzleWindow(GameController control) {
		
		
	}
	/**
	 * Initializes the interface components, buttons and spinners
	 */
	private void initialize() {
		
	}
	
	

}

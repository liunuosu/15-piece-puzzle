import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Allows user to visualize 15-Puzzle simulation
 * 
 */
public class PuzzleWindow extends JFrame {

	private static final long serialVersionUID = -6629882116604217240L;
	// panel for visualizing the game
	private PuzzlePanel boardPanel;
	// controller that allows game to be simulated
	private GameController control;

	// Buttons and Functionalities
	// button for starting new game
	private JButton newGame;
	// button for using A* solver to solve the game
	private JButton solverA;
	// button for using Human Algorithm solver to solve the game
	private JButton solverH;
	// button for showing step-by-step solution
	private JButton showSolution;
	// button for showing the next step of the solution
	private JButton nextStep;
	// button for showing the final solution and number of steps to solution
	private JButton endResult;
	// label for displaying number of steps to solution
	private JLabel moves;
	// total number of steps to solution
	private int numberOfMoves;
	// Active animation is performed by this Thread
	private Thread running;

	/**
	 * Creates a window for visualizing the 15-Puzzle game
	 * 
	 * @param control GameController for performing actions is the game
	 */
	public PuzzleWindow(GameController control) {
		super();
		// sets the title of the window
		setTitle("15-Puzzle Visualization");
		// sets size of window
		setSize(800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		// initializes global variable GameController
		this.control = control;
		// initializes all buttons and functionalities
		initialize();

	}

	/**
	 * Initializes the buttons and labels at their desired location in the screen
	 */
	private void initialize() {
		setLayout(new BorderLayout());

		// creates a puzzle panel, which is the grid of tiles in the game
		boardPanel = new PuzzlePanel(control.getBoard());
		add(boardPanel, BorderLayout.CENTER);
		boardPanel.updatePuzzle();

		// Aggregates all buttons into a panel that will be added to the bottom of the
		// page
		JPanel buttons = new JPanel();

		// button for using A* solver to solve the game
		solverA = new JButton("Solver A*");
		solverA.addActionListener(solverA());
		solverA.setBackground(Color.WHITE);
		buttons.add(solverA);

		// button for showing step-by-step solution
		solverH = new JButton("Human Solver");
		solverH.addActionListener(solverH());
		solverH.setBackground(Color.WHITE);
		buttons.add(solverH);

		// button for showing step-by-step solution
		showSolution = new JButton("Show Solution");
		showSolution.addActionListener(showSolution());
		showSolution.setBackground(Color.WHITE);
		buttons.add(showSolution);

		// button for showing the next step of the solution
		nextStep = new JButton("Next Step");
		nextStep.addActionListener(nextStep());
		nextStep.setBackground(Color.WHITE);
		buttons.add(nextStep);

		// button for showing the final solution and number of steps to solution
		endResult = new JButton("Final Result");
		endResult.addActionListener(endResult());
		endResult.setBackground(Color.WHITE);
		buttons.add(endResult);

		buttons.add(new JLabel("Total number of moves made: "), BorderLayout.EAST);
		// label for displaying number of steps to solution
		moves = new JLabel();
		moves.setVisible(false);
		buttons.add(moves, BorderLayout.EAST);

		// Adds the control panel to the bottom of the window
		add(buttons, BorderLayout.SOUTH);
		buttons.setBackground(Color.WHITE);

		// button for starting new game
		// this is added to the top of the window, which is why JPanel buttons does not
		// contain it
		newGame = new JButton("NEW GAME");
		newGame.addActionListener(newGame());
		add(newGame, BorderLayout.NORTH);
		newGame.setBackground(Color.WHITE);
		newGame.setForeground(new Color(255, 20, 147));
	}

	/**
	 * Method for using A* algorithm to solve the puzzle
	 * 
	 * @return an ActionListener that detects user's clicks
	 */
	private ActionListener solverA() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// selects the appropriate solver in global GameController
				control.setSolver("A-star");
				// solves puzzle using appropriate solver
				control.solver();
				// records number of moves to final solution
				numberOfMoves = control.numberOfTotalSteps();
			}
		};
	}

	/**
	 * Method for using Human algorithm to solve the puzzle The trigger for the
	 * button solverH in the window
	 * 
	 * @return an ActionListener that detects user's clicks
	 */
	private ActionListener solverH() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// selects the appropriate solver in global GameController
				control.setSolver("Human");
				// solves puzzle using appropriate solver
				control.solver();
				// records number of moves to final solution
				numberOfMoves = control.numberOfTotalSteps();

			}
		};
	}

	/**
	 * Method for animating and displaying step-by-step solution of the puzzle The
	 * trigger for the button showSolution in the window
	 * 
	 * @return an ActionListener that detects user's clicks
	 */
	private ActionListener showSolution() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// If no animation is currently running, start new animation
				if (running == null) {
					// changes name of the button to stop
					showSolution.setText("Stop");
					running = new AnimateThread();
					// start the animation
					running.start();
					// disable other buttons, so user cannot triger other actions
					solverA.setEnabled(false);
					solverH.setEnabled(false);
					nextStep.setEnabled(false);
					endResult.setEnabled(false);
					newGame.setEnabled(false);
				}
				// If there is an animation running, stop the animation
				else {
					// set the instance variable to null to indicate the thread should stop
					running = null;
					// enable all buttons again
					showSolution.setText("Show Solution");
					solverA.setEnabled(true);
					solverH.setEnabled(true);
					nextStep.setEnabled(true);
					endResult.setEnabled(true);
					newGame.setEnabled(true);

				}
			}
		};
	}

	/**
	 * Method for displaying next step in the solution
	 * 
	 * @return an ActionListener that detects user's clicks
	 */
	private ActionListener nextStep() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// selects step in the solution to be displayed
				control.moveTile();
				// changes the puzzle to be printed
				boardPanel.setPuzzle(control.getBoard());
				// prints updated puzzle
				boardPanel.updatePuzzle();

			}
		};
	}

	/**
	 * Method for displaying the puzzle solved and number of steps it took to
	 * solution
	 * 
	 * @return an ActionListener that detects user's clicks
	 */
	private ActionListener endResult() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// updates label for displaying number of steps
				moves.setText(String.valueOf(numberOfMoves));
				// shows number on window
				moves.setVisible(true);
				// updates panel to solved board
				boardPanel.setPuzzle(control.getSolvedBoard());
				// prints solved puzzle
				boardPanel.updatePuzzle();
			}
		};
	}

	/**
	 * 
	 * @return
	 * @author Kes
	 */
	private ActionListener newGame() {

		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// comment
				control.createNewGame();
				boardPanel.setPuzzle(control.getBoard());
				numberOfMoves = 0;
				moves.setText(String.valueOf(numberOfMoves));
				moves.setVisible(false);
				// comment
				boardPanel.updatePuzzle();
			}
		};
	}

	/**
	 * This inner class extends Thread for the purpose of animating the simulation
	 * 
	 */
	private class AnimateThread extends Thread {
		public AnimateThread() {
			super();
			// Let this Thread be a Daemon, so that it will end if all other Threads have
			// ended
			this.setDaemon(true);
		}

		@Override
		public void run() {
			while (running == this) {
				// allows simulation to move on tile in the direction of the solution
				control.moveTile();
				// updates the panel that should be displayed
				boardPanel.setPuzzle(control.getBoard());
				// prints updated panel
				boardPanel.updatePuzzle();
				try {
					// Sleep 400ms so that animation is perceptible to user
					Thread.sleep(400);
				} catch (Exception e) {
				}
			}
		}
	}

}

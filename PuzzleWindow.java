import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSpinner;

/**
 * Allows user to visualize 15-Puzzle simulation
 * 
 * @author Kes and Liz
 *
 */
public class PuzzleWindow extends JFrame {

	private static final long serialVersionUID = -6629882116604217240L;
	// panel for visualizing the game
	private PuzzlePanel boardPanel;
	// game to be simulated
	private GameController control;

	// Interface Components
	// button for starting new game
	private JButton newGame;
	// spinner for selecting solver to be used
	private JSpinner solverType;
	// spinner for showing step by step solution
	private JSpinner showSolution;
	// spinner for showing number of iterations
	private JSpinner showIterations;

	// If an animation is active it is performed by this Thread
	private Thread running;

	/**
	 * Creates a window for visualizing the desired game
	 * 
	 * @param game
	 */
	public PuzzleWindow(GameController control) {
		super();
		setTitle("CreatureWorld Visualization");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		this.control = control;
		initialize();

	}

	/**
	 * Initializes the interface components, buttons and spinners Liz
	 */
	private void initialize() {
		setLayout(new BorderLayout());

		// The WorldPanel shows the visualization and should be central in the window
		boardPanel = new PuzzlePanel(control);
		add(boardPanel, BorderLayout.CENTER);
		boardPanel.updatePuzzle();

		// At the bottom we will contruct a panel with some button to control the
		// simulation
		JPanel buttons = new JPanel();

		// Spinner to control the pause between animation steps
		String[] solvers = { "A* solver", "H* solver" };
		solverType = new JSpinner(new SpinnerListModel(solvers));
		buttons.add(solverType);

		// Buton to start an animation
		showSolution = new JButton("Show Solution");
		showSolution.addActionListener(showSolution());
		showSolution.setBackground(Color.WHITE);
		buttons.add(showSolution);

		// Button for the purpose of spawning a random creature
		nextStep = new JButton("Next Step");
		nextStep.addActionListener(nextStep());
		nextStep.setBackground(Color.WHITE);
		buttons.add(nextStep);

		// Button for the purpose of randomizing the wealth of the cells
		endResult = new JButton("End Result");
		endResult.addActionListener(endResult());
		endResult.setBackground(Color.WHITE);
		buttons.add(endResult);

		buttons.add(new JLabel("Number of moves made: "), BorderLayout.EAST);
		
		// Adds the control panel to the bottom of the window
		add(buttons, BorderLayout.SOUTH);
		buttons.setBackground(Color.WHITE);

		// Button for the purpose of randomizing the wealth of the cells
		newGame = new JButton("NEW GAME");
		newGame.addActionListener(newGame());
		add(newGame, BorderLayout.NORTH);
		newGame.setBackground(Color.WHITE);
		newGame.setForeground(new Color(255, 20, 147));	
	}

	/**
	 * 
	 * @return
	 * @author Liz
	 */
	private ActionListener showSolution() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		};
	}

	/**
	 * 
	 * @return
	 * @author Liz
	 */
	private ActionListener nextStep() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		};
	}

	/**
	 * 
	 * @return
	 * @author Liz
	 */
	private ActionListener endResult() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

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
			public void actionPerformed(ActionEvent arg0) {

			}
		};
	}

	/**
	 * 
	 * @return
	 * @author Kes
	 */
	private ActionListener solverType() {
		// Create an anonymous inner class
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		};
	}

	/**
	 * Inner class which extends Thread for the purpose of animating the simulation
	 * 
	 * @author Kes
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
				// Let the controller do a step
				// controller.step();
				// Let the WorldPanel redraw the state of the world
				// worldPanel.updateWorld();
				try {
					// Sleep for the specified amount of time
					// int time = ((Number)spinner.getValue()).intValue();
					// Thread.sleep(time);
				} catch (Exception e) {
				}
			}
		}
	}

}

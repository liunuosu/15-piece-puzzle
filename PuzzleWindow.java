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

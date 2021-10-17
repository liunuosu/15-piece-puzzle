import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class PuzzlePanel extends JPanel {

	private static final long serialVersionUID = -5618021916746794320L;
	// Background Color
	private static final Color FOREGROUND_COLOR = new Color(255, 20, 147);
	// the controller of the simulation
	private GameBoard control;
	//
	private ArrayList<Integer> game = new ArrayList<Integer>();
	// the size of the grid
	private int gridSize;
	// size of the tiles
	private int tileSize;
	// the margin between tiles
	private final int margin = 30;
	// the dimension of the grid
	private final int dimension = 600;
	// size of the game (4x4)
	private int size;
	//
	private boolean solved;
	// The current drawing of the World state
	private BufferedImage currentImage;

	/**
	 * 
	 * @param initial
	 * @param size
	 */
	public PuzzlePanel(GameBoard initial) {
		super();
		this.control = initial;
		this.size = 4;
		this.game = control.getBoard();

		this.gridSize = (this.dimension - 2 * this.margin);
		this.tileSize = this.gridSize / this.size;

		setPreferredSize(new Dimension(this.dimension, this.dimension + this.margin));
		setBackground(Color.WHITE);
		setForeground(FOREGROUND_COLOR);
		setFont(new Font("SansSerif", Font.BOLD, 60));

		solved = true;
		updatePuzzle();
		
	}

	public int tileSize() {
		return tileSize;
	}

	@Override
	public void paintComponent(Graphics grid) {
		
		super.paintComponent(grid);
	    Graphics2D g2D = (Graphics2D) grid;
	    g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    drawGrid(g2D);
	    //drawMessage(g2D);
	    
	}

	/**
	 * 
	 */
	public void updatePuzzle() {
		
		System.out.println("updatePuzzle");
		//this.game = control.getBoard(); // ADDED BY KES
		//System.out.println(this.game);
		int h = this.gridSize;
		int w = h;
		
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) newImage.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		drawGrid(g);
		currentImage = newImage;
		repaint();
		System.out.println("updatePuzzle2");

	}
	
	public void setPuzzle(GameBoard gameBoard) {
		this.game = gameBoard.getBoard();
	}

	/**
	 * Draws grid and tiles on the screen, we define dimensions, number of cells and
	 * contents of cells
	 * 
	 * @param gr where the grid should be drawn
	 */
	private void drawGrid(Graphics2D grid) {

		for (int i = 0; i < this.game.size(); i++) {
			// we convert 1D coords to 2D coords given the size of the 2D Array
			int row = i / this.size;
			int column = i % this.size;
			// we convert in coords on the UI
			int x = this.margin + column * this.tileSize;
			int y = this.margin + row * this.tileSize;

			// check special case for blank tile
			if (this.game.get(i) == 0) {
				if (solved) {
					grid.setColor(FOREGROUND_COLOR);
					drawCenteredString(grid, " ", x, y);
				}

				continue;
			}
			// for other tiles
			grid.setColor(getForeground());
			grid.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
			grid.setColor(Color.WHITE);
			grid.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
			grid.setColor(Color.WHITE);

			drawCenteredString(grid, String.valueOf(this.game.get(i)), x, y);
		}
	}

	/**
	 * Draws a text at the center of a grid
	 * 
	 * @param g the grid to be drawn in
	 * @param s the message to be drawn
	 * @param x x-coordinate of grid
	 * @param y y-coordinate of grid
	 */
	private void drawCenteredString(Graphics2D grid, String number, int x, int y) {

		// center string s for the given tile (x,y)
		FontMetrics fm = grid.getFontMetrics();
		int asc = fm.getAscent();
		int desc = fm.getDescent();
		grid.drawString(number, x + (tileSize - fm.stringWidth(number)) / 2, y + (asc + (tileSize - (asc + desc)) / 2));

	}

}

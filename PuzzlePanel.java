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

/**
 * This class allows the grid of tiles to be displayed and updated whenever a
 * puzzle changes
 * 
 *
 */
public class PuzzlePanel extends JPanel {

	private static final long serialVersionUID = -5618021916746794320L;
	// background color for the tiles
	private static final Color FOREGROUND_COLOR = new Color(255, 20, 147);
	// the game board to be displayed
	private GameBoard control;
	// the array of integers in the board according to their order
	private ArrayList<Integer> game = new ArrayList<Integer>();
	// the size of the grid
	private int gridSize;
	// size of the tiles
	private int tileSize;
	// the margin between tiles and border
	private final int margin = 30;
	// the dimension of the grid
	private final int dimension = 600;
	// size of the game (i.g 4x4)
	private int size = 4;
	// signals whether the game can be displayed
	private boolean solved;

	/**
	 * This constructor creates a panel where the tiles will be displayed
	 * 
	 * @param initial the first board to be displayed
	 */
	public PuzzlePanel(GameBoard initial) {
		// initializes the global variables
		super();
		this.control = initial;
		this.game = control.getBoard();
		// determines total size of grid and size of each til
		this.gridSize = (this.dimension - 2 * this.margin);
		this.tileSize = this.gridSize / this.size;
		// determines dimension of grid
		setPreferredSize(new Dimension(this.dimension, this.dimension + this.margin));
		// determine background of grid and color of tiles
		setBackground(Color.WHITE);
		setForeground(FOREGROUND_COLOR);
		// determines font to be used to display tile numbers
		setFont(new Font("Century Gothic", Font.BOLD, 60));

		solved = true;
		// prints initial grid
		updatePuzzle();

	}

	@Override
	public void paintComponent(Graphics grid) {
		super.paintComponent(grid);
		// transforms input into Graphics2D so that grid can be displayed in better
		// quality
		Graphics2D g2D = (Graphics2D) grid;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// draws grid
		drawGrid(g2D);

	}

	/**
	 * This method allows us to display a grid. When the puzzle changes during its
	 * solution, this method allows tile moves to be displayed.
	 */
	public void updatePuzzle() {
		// determines length and height of grid
		int h = this.gridSize;
		int w = h;
		// creates a new image of what the grid should look like
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D) newImage.getGraphics();
		// determines color and draws the grid
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		drawGrid(g);
		// paints the updated grid on screen
		repaint();

	}

	/**
	 * This method allows for desired board to be drawn to be updated
	 * 
	 * @param gameBoard the new board to be drawn
	 */
	public void setPuzzle(GameBoard gameBoard) {
		this.game = gameBoard.getBoard();
	}

	/**
	 * Draws grid and tiles on the screen, according to defined dimensions, number
	 * of tiles, and tile numbers.
	 * 
	 * @param grid where the grid should be drawn
	 */
	private void drawGrid(Graphics2D grid) {

		for (int i = 0; i < this.game.size(); i++) {
			// converts 1D coordinates to 2D coordinates, so we can draw each tile at it's
			// appropriate location
			int row = i / this.size;
			int column = i % this.size;
			// determines size of tile and location on screen
			int x = this.margin + column * this.tileSize;
			int y = this.margin + row * this.tileSize;

			// draws the blank tile at it's appropriate location
			if (this.game.get(i) == 0) {
				if (solved) {
					grid.setColor(FOREGROUND_COLOR);
				}

				continue;
			}
			// for other tiles sets background color, tile size, tile location
			grid.setColor(getForeground());
			grid.fillRoundRect(x, y, tileSize, tileSize, 25, 25);
			grid.setColor(Color.WHITE);
			grid.drawRoundRect(x, y, tileSize, tileSize, 25, 25);
			grid.setColor(Color.WHITE);
			// draws number of corresponding tile in the middle
			drawNumber(grid, String.valueOf(this.game.get(i)), x, y);
		}
	}

	/**
	 * Draws a String at the center of a tile
	 * 
	 * @param g the grid to be drawn in
	 * @param s the number to be drawn
	 * @param x x-coordinate of grid
	 * @param y y-coordinate of grid
	 */
	private void drawNumber(Graphics2D grid, String number, int x, int y) {

		// finds the center of tile where number should be drawn
		FontMetrics fm = grid.getFontMetrics();
		int asc = fm.getAscent();
		int desc = fm.getDescent();
		int x_coord = x + (tileSize - fm.stringWidth(number)) / 2;
		int y_coord = y + (asc + (tileSize - (asc + desc)) / 2);
		// draws number at the center
		grid.drawString(number, x_coord, y_coord);

	}

}

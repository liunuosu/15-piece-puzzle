
	import java.awt.BorderLayout;
	import java.util.ArrayList;
	import java.util.Arrays;

	import javax.swing.JFrame;
	import javax.swing.SwingUtilities;



	public class Test {
		
		
		public static void main(String[] args) {
			
			ArrayList<Integer> perfectList = new ArrayList<Integer>();
			
			perfectList.add(1);
			perfectList.add(2);
			perfectList.add(3);
			perfectList.add(4);
			perfectList.add(5);
			perfectList.add(6);
			perfectList.add(7);
			perfectList.add(8);
			perfectList.add(9);
			perfectList.add(10);
			perfectList.add(11);
			perfectList.add(12);
			perfectList.add(13);
			perfectList.add(14);
			perfectList.add(15);
			perfectList.add(0);
			
			//GameBoard game = new GameBoard(perfectList);
			Puzzle puzzle = new Puzzle();
			GameBoard game = new GameBoard(puzzle.getPuzzle());
			//System.out.println(game.checkIfComplete());
			 
			GameController controller = new GameController(game, "A* solver");
			PuzzleWindow pw = new PuzzleWindow(controller);
			pw.setVisible(true);
			
		}
		

	}
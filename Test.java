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
		
		GameBoard game = new GameBoard(perfectList);
		
		PuzzlePanel panel = new PuzzlePanel(game);
		
		 SwingUtilities.invokeLater(() -> {
		      JFrame frame = new JFrame();
		      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		      frame.setTitle("Game of Fifteen");
		      frame.setResizable(false);
		      frame.add(panel, BorderLayout.CENTER);
		      frame.pack();
		      // center on the screen
		      frame.setLocationRelativeTo(null);
		      frame.setVisible(true);
		    });
		
	}
	

}

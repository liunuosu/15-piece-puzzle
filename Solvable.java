
public class Solvable {
	//input must be 1D array with numbers read from left to right and then top to bottom, 0 for X
		public static boolean solvable(int[] input) {
			boolean solvable = false;
			int inversion = 0;
			int row = 0; //row in which the 0 is
			int rowFromBottom = 0;
			
			for (int i = 0; i < input.length; i++) {
				for (int j = i+1; j < input.length; j++) {
					if (input[i]> input[j] && input[j] > 0 && input[i] > 0) {
						inversion++;
					}
				}
			}
			
			for (int i = 0; i<input.length; i++) {
				if (input[i]==0 && i%4 != 0) {
					row = (int) Math.ceil(i / 4.0);
				}
				else if (input[i] == 0 && i%4 == 0) {
					row = (int) Math.ceil(i/4.0) + 1;
				}
			}
			
			rowFromBottom = 4-row+1;
			
			if(rowFromBottom%2 == 0 && inversion%2 !=0) { //rowFromBottom is even
				solvable = true;
			}
			else if (rowFromBottom%2 != 0 && inversion%2 == 0) { 
				solvable = true;
			}
			
			return solvable;
		}
}

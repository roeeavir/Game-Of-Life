import java.util.Random;
import java.util.Scanner;

public class HW2_RoeeAviran {
	public static boolean[][] testBoard;
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.printf("******\tGame  Of  Life  On Board X * X\t****** \nEnter X Size:\n");
		int size = input.nextInt();
		System.out.println("Enter Max Number Of Boards:");
		int numOfBoards = input.nextInt();
		boolean[][] board = new boolean[size][size];
		board = getStartBoard(size, board);
		testBoard = copyBoard(board);
		String matrix = toString(board);
		System.out.print("Start Board:" + matrix);
		matrix = gameSteps(size, numOfBoards, matrix, board);
		// Stop the program and enter your desired 2d boolean array - true = 1 false = 0.
		System.out.println("\n\n*Testing Phase*\nPlease Insert Your Wanted 2D Boolean Array (You May Change The One"
				+ " Already Existing In The Code):");
		boolean customBoard[][] = {{false,true,true,false,false},{false,true,false,true,false},
				{false,true,true,true,false},{false,true,false,true,false},{true,false,false,false,false}};
		boolean isSame = setBoard(customBoard);
		if (isSame) {
			System.out.println("Action Completed!");
		}
		System.out.println("\nEnd");
		input.close();

	}
	// A method for defining the wanted board size as a 2d boolean array and randomizing its starting variables.
		public static boolean[][] getStartBoard(int x, boolean[][] board) {
			board = new boolean[x][x];
			Random random = new Random();
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					board[i][j] = random.nextBoolean();
				}
			}
			return board;
		}
		// A method for creating a matrix for binary variables extracted from The board boolean array.
		public static String toString(boolean[][] board) {
			int binary = 0;
			String matrix = "\n";
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j]) {
						binary = 1;
					}
					else {
						binary = 0;
					}
					matrix += " " + binary;
				}
				matrix += "\n";
			}
			return matrix;
		}
		
		// A method for copying the existing matrix into a new 2d boolean array.
		public static boolean[][] copyBoard(boolean[][] board){
			boolean[][] newBoard = new boolean[board.length][board.length]; 
			for (int i = 0; i < newBoard.length; i++) {
				for (int j = 0; j < newBoard.length; j++) {
					newBoard[i][j] = board[i][j];
				}
				
			}
			return newBoard;
		}
		
//		// A method for checking a 2d boolean array and changing it as intended in the rules.
		public static boolean[][] checkBoard(boolean[][] newBoard, boolean[][] board){
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					board[i][j] = setCell(i, j, newBoard, board);
					
				}
			}
			return board;
		}
		
		// A method for checking each individual slot in the 2d boolean array and updating it one by one.
		public static boolean setCell(int x, int y, boolean[][] newBoard, boolean[][] board){
			int neighbors = getNeighbors(x , y, newBoard);
			if (neighbors == 3) {
				board[x][y] = true;
			}
			else if (neighbors < 2 || neighbors > 4) {
				board[x][y] = false;
			}
		
			return board[x][y];
		}
		
//		/*A method for checking each individual slot in the 2d boolean array and its surrounding and giving back
//		the number of "living neighbors" of each slot.*/
		public static int getNeighbors(int x, int y, boolean[][] newBoard) {
			int neighbors = 0;
			for (int i = -1; i <= 1; i++) {
				for (int j = -1; j <= 1; j++) {
					int ix = i + x;
					int jy = j + y;
					// There is no need to check the board (the cell won't be able to influence itself).
					if (!(jy == y && ix == x)) {
						/* Because there are no bound, if we would like to check the neighbors "below" the lowest array line/column,
						we will be actually checking the last line/column. It will also work the other way around.*/
						if (ix < 0) {			
							ix = newBoard.length-1;
						}
						if (jy < 0) {
							jy = newBoard[x].length-1;
						}
						if (ix == newBoard.length) {
							ix = 0;
						}
						if (jy == newBoard[x].length) {
							jy = 0;
						}
						if (newBoard[ix][jy]) {
							neighbors++;
						}
					}
				}
			}
			return neighbors;
		}
		
		// A method for checking if the new board is any different from the previous one.
		public static boolean isIdentical(boolean[][] newBoard, boolean[][] board) {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board.length; j++) {
					if (board[i][j] != newBoard[i][j]) {
						return true;
					}
				}
			}
			return false;
		}
		
		// A method for looping the created functions for the wanted number of boards, or untill the board cannot be changed anymore.
		public static String gameSteps(int size, int numOfBoards, String matrix, boolean[][] board) {
			int count = 0;
			boolean[][] newBoard;
			for (int i = 0; i < numOfBoards; i++) {
				newBoard = copyBoard(board);
				board = checkBoard(newBoard, board);
				if (!isIdentical(newBoard, board)) {
					break;
				}
				count++;
				matrix = toString(board);
				System.out.printf("\nStep %d" + matrix + "Board Changed.\n", count);	
			}
			System.out.printf("\nFinished!!!%sBoard Changed %d Times!", matrix, count);
			return matrix;
		}
		
		/* A method which lets the user know the state of the "board" after any number of step, in the form of
		a 2d array - True means a living cell.*/
		public static boolean[][] moveTimeBy(int numberOfSteps){
			boolean[][] previewBoard;
			for (int i = 0; i < numberOfSteps; i++) {
				previewBoard = copyBoard(testBoard);
				testBoard = checkBoard(previewBoard, testBoard);
			}
			return testBoard;
		}
		
		// A method that is getting a 2d boolean array and is giving feedback for if the boards the same size or not.
		public static boolean setBoard(boolean[][] customBoard) {
			System.out.println("Enter Your Desired Number Of Steps:");
			Scanner input = new Scanner(System.in);
			// Enter the number of steps you desire for the new matrix to undergo. 
			int numberOfSteps = input.nextInt();
			input.close();
			for (int i = 0; i < customBoard.length; i++) {
				if (testBoard.length != customBoard.length || customBoard.length != customBoard[i].length) {
					System.err.print("The Desired 2D Boolean Array Is Not The Same Size As The Original One, Therefore"
							+ " The Action Cannot Be Continued");
					return false;
				}
			}
			testBoard = copyBoard(customBoard);
			testBoard = moveTimeBy(numberOfSteps);
			String matrix = toString(testBoard);
			System.out.print("your new matrix is:" + matrix);
			return true;
		}
}
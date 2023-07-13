import java.util.Scanner;

// Algorithms explained: minimax and alpha-beta pruning
// https://www.youtube.com/watch?v=l-hh51ncgDI

public class TicTacToeTest {

	public static void main(String[] args) {
			
		int boardSize = 3;
		TicTacToeState board = new TicTacToeState(boardSize,2);
		//AdversialSearch adversialSearch = new Minimax();
		AdversialSearch adversialSearch = new AlphaBetaPruning();
		
		for (int y=0; y<boardSize; y++) {
			for (int x=0; x<boardSize; x++) {
				System.out.print((y * boardSize + x) + " ");
			}
			System.out.println();
		}
		System.out.println();

		int playerIndex = 1;
		Scanner in = new Scanner(System.in);		
		Solution solution;
		do {
			playerIndex++;
			
			if (playerIndex % 2 == 0) {
				// Player 1 : CPU AI
				System.out.println("AI move \n-------");
				solution = adversialSearch.getNextMove(board, Player.One);
				board = (TicTacToeState)solution.nextMove;
				System.out.println(board + " --> " + solution.utility + " (# = " + adversialSearch.getExpandedStateCount() + ")");
				System.out.println();
				if (board.wins(Player.One)) {
					System.out.println("AI player wins");
					break;
				}
				if (board.isTie()) {
					System.out.println("Tie");
					break;
				}
			}
			else {
				// Player 2 : Human
				System.out.println("Human move \n----------");
				boolean isValid;
				do {
					int moveIndex = in.nextInt();
					int x = moveIndex % boardSize;
					int y = moveIndex / boardSize;
					isValid = board.set(x, y, Player.Two);
				}
				while (!isValid);
				
				System.out.println(board);
				if (board.wins(Player.Two)) {
					System.out.println("Human player wins");
					break;
				}
				if (board.isTie()) {
					System.out.println("Tie");
					break;
				}
			}
		}
		while (true);			
		
		in.close();
	}

}

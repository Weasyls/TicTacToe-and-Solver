
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

enum CellState {
    X, O, W
};

public class TicTacToeState extends BoardState {
	//PUSH MY ASSS
    final int boardSize;
    private final CellState boardState[][];

    TicTacToeState(int boardSize, int ratio) {
        boardSize = Math.max(boardSize, 3);
        this.boardSize = boardSize;

        boardState = new CellState[boardSize][boardSize];
        Random rd = new Random();
        
        for (int i = 0; i < boardSize; i++) {
            int r1 = rd.nextInt(boardSize - 1);
            int r2 = rd.nextInt(boardSize - 1);
            
            if(!set(r1, r2, Player.Wall)){
                i--; 
                continue;
            }
            System.out.println(r1 + " " + r2);
        }
    }

    TicTacToeState(TicTacToeState state) {
        this.boardSize = state.boardSize;

        boardState = new CellState[boardSize][boardSize];

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                this.boardState[y][x] = state.boardState[y][x];
            }
        }
    }

    @Override
    public BoardState clone() throws CloneNotSupportedException {
        return new TicTacToeState(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof TicTacToeState) {
            TicTacToeState state = (TicTacToeState) obj;
            for (int y = 0; y < boardSize; y++) {
                for (int x = 0; x < boardSize; x++) {
                    if (this.boardState[y][x] != state.boardState[y][x]) {
                        return false;
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        long code = 0;

        for (int i = 0; i < boardSize * boardSize; i++) {
            int x = i % boardSize;
            int y = i / boardSize;
            code = (code << 2) | (boardState[y][x].ordinal() + 1);
        }

        return new Long(code).hashCode();
    }

    boolean set(int x, int y, Player player) {
        if (boardState[y][x] == null) {
            if(player.equals(Player.Wall))
                boardState[y][x] = CellState.W;
            else
                boardState[y][x] = (player == Player.One ? CellState.X : CellState.O);

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean wins(Player player) {
        Double utility = getUtility(player);
        return (utility != null && getUtility(player) > 0.0);
    }

    @Override
    public boolean loses(Player player) {
        Double utility = getUtility(player);
        return (utility != null && getUtility(player) < 0.0);
    }

    @Override
    public boolean isTie() {
        Double utility1 = getUtility(Player.One);
        Double utility2 = getUtility(Player.Two);
        return (utility1 != null && utility2 != null && (utility1 == 0 || utility2 == 0));
    }

    @Override
    public Double getUtility(Player player) {
        int emptyCellCount = 0;
        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (boardState[y][x] == null) {
                    emptyCellCount++;
                }
            }
        }

        CellState playerState = (player == Player.One ? CellState.X : CellState.O);
        CellState opponentState = (player == Player.One ? CellState.O : CellState.X);

        int playerCompleteCount = 0;
        int opponentCompleteCount = 0;
        for (int i = 0; i < boardSize; i++) {
            // vertical
            if (boardState[0][i] == playerState && isComplete(i, 0, 0, 1)) {
                playerCompleteCount++;
            }
            if (boardState[0][i] == opponentState && isComplete(i, 0, 0, 1)) {
                opponentCompleteCount++;
            }

            // horizontal
            if (boardState[i][0] == playerState && isComplete(0, i, 1, 0)) {
                playerCompleteCount++;
            }
            if (boardState[i][0] == opponentState && isComplete(0, i, 1, 0)) {
                opponentCompleteCount++;
            }
        }

        // diagonal
        if ((boardState[0][0] == playerState && isComplete(0, 0, 1, 1))
                || (boardState[0][boardSize - 1] == playerState && isComplete(boardSize - 1, 0, -1, 1))) {
            playerCompleteCount++;
        }
        if ((boardState[0][0] == opponentState && isComplete(0, 0, 1, 1))
                || (boardState[0][boardSize - 1] == opponentState && isComplete(boardSize - 1, 0, -1, 1))) {
            opponentCompleteCount++;
        }

        if (playerCompleteCount == 0 && opponentCompleteCount == 0) {
            return (emptyCellCount == 0 ? 0.0 : null);
        }

        if (playerCompleteCount > 0) {
            return (double) playerCompleteCount;
        } else {
            return -1.0 * opponentCompleteCount;
        }
    }

    boolean isComplete(int x, int y, int deltax, int deltay) {
        for (int i = 1; i < boardSize; i++) {
            if (boardState[y][x] != boardState[y + i * deltay][x + i * deltax]) {
                return false;
            }
        }

        return true;
    }

    @Override
    public List<BoardState> getChildList(Player player) {
        List<BoardState> children = new ArrayList<BoardState>();

        CellState playerState = (player == Player.One ? CellState.X : CellState.O);

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (boardState[y][x] == null) {
                    TicTacToeState childState = new TicTacToeState(this);
                    childState.boardState[y][x] = playerState;
                    children.add(childState);
                }
            }
        }

        return children;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (boardState[y][x] == null) {
                    sb.append(".");
                } else {
                    sb.append(boardState[y][x].toString());
                }
            }
            sb.append('\n');
        }

        return sb.toString();
    }

}


public class AlphaBetaPruning extends AdversialSearch {

	AlphaBetaPruning()
	{
	}
	
	AlphaBetaPruning(int maximumDepth) {
		super(maximumDepth);
	}
	
	@Override
	Solution getNextMove(BoardState initialState, Player player)
	{
		nextMove = null;
		expandedStateCount = 0;
	
		Player opponent = (player == Player.One ? Player.Two : Player.One);		
		double alpha = Double.NEGATIVE_INFINITY;
		double beta = Double.POSITIVE_INFINITY;
		double utility = getNextMove(initialState, player, opponent, 0, alpha, beta);
		
		return new Solution(nextMove, utility);
	}
	
	double getNextMove(BoardState currentState, Player player, Player opponent, int depth, double alpha, double beta)
	{
		expandedStateCount++;
		
		Double currentUtility = currentState.getUtility(player);
		if (depth == maximumDepth || currentUtility != null) {
			return currentUtility;
		}
		
		boolean isMaximizingPlayer = (depth % 2 == 0);		
		if (isMaximizingPlayer) {
			double maxUtility = Double.NEGATIVE_INFINITY;
			for (BoardState childState : currentState.getChildList(player)) {
				double utility = getNextMove(childState, player, opponent, depth+1, alpha, beta);
				if (depth == 0 && utility > maxUtility) {
					nextMove = childState;
				}
				maxUtility = Math.max(utility, maxUtility);
				alpha = Math.max(alpha, utility);
				if (beta <= alpha) {
					break;
				}
			}
			return maxUtility;
		}
		else {
			double minUtility = Double.POSITIVE_INFINITY;
			for (BoardState childState : currentState.getChildList(opponent)) {
				double utility = getNextMove(childState, player, player, depth+1, alpha, beta);
				minUtility = Math.min(utility, minUtility);
				beta = Math.min(beta, utility);
				if (beta <= alpha) {
					break;
				}				
			}
			return minUtility;
		}
	}
	
}

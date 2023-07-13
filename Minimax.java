
public class Minimax extends AdversialSearch {
	
	Minimax()
	{
	}
	
	Minimax(int maximumDepth) 
	{
		super(maximumDepth);
	}
	
	@Override
	Solution getNextMove(BoardState initialState, Player player)
	{
		nextMove = null;
		expandedStateCount = 0;
	
		Player opponent = (player == Player.One ? Player.Two : Player.One);		
		double utility = getNextMove(initialState, player, opponent, 0);
		
		return new Solution(nextMove, utility);
	}
	
	double getNextMove(BoardState currentState, Player player, Player opponent, int depth)
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
				double utility = getNextMove(childState, player, opponent, depth+1);
				if (depth == 0 && utility > maxUtility) {
					nextMove = childState;
				}
				maxUtility = Math.max(utility, maxUtility);
			}
			return maxUtility;
		}
		else {
			double minUtility = Double.POSITIVE_INFINITY;
			for (BoardState childState : currentState.getChildList(opponent)) {
				double utility = getNextMove(childState, player, player, depth+1);
				minUtility = Math.min(utility, minUtility);
			}
			return minUtility;
		}
	}
	
}

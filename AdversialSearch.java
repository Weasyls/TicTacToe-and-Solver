
public abstract class AdversialSearch {

	public final int maximumDepth;
	protected BoardState nextMove;
	protected int expandedStateCount;
	
	AdversialSearch()
	{
		maximumDepth = Integer.MAX_VALUE;
	}
	
	AdversialSearch(int maximumDepth) 
	{
		this.maximumDepth = maximumDepth;
	}
		
	int getExpandedStateCount()
	{
		return expandedStateCount; 
	}
	
	abstract Solution getNextMove(BoardState initialState, Player player);
	
}

import java.util.List;

enum Player {One, Two, Wall};

public abstract class BoardState implements Cloneable {
		
	abstract public boolean wins(Player player);

	abstract public boolean loses(Player player);
	
	abstract public boolean isTie();
	
	abstract public Double getUtility(Player player);
	
	abstract public List<BoardState> getChildList(Player player);
		
	abstract public BoardState clone() throws CloneNotSupportedException;
}

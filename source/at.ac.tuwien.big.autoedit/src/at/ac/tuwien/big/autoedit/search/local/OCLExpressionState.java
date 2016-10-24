package at.ac.tuwien.big.autoedit.search.local;

public interface OCLExpressionState<U extends Comparable<U>> {
	
	public LocalSearchInterface getLocalSearch();
	
	public ChangeQuickfix<?,U> getQuickfixProvider();

}

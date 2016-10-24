package at.ac.tuwien.big.autoedit.fixer;

public interface BorderGenerator<T> {
	
	public T[] getBorders(T val);
	
	public default T getLowerBorder(T val) {
		return getBorders(val)[0];
	}
	
	public default T getUpperBorder(T val) {
		return getBorders(val)[1];
	}
	

}

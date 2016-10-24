package at.ac.tuwien.big.autoedit.scope;

import java.util.Map.Entry;

public interface ComparablePair<T,U extends Comparable<U>> extends Comparable<ComparablePair<T,U>> {
	
	public T getSolution();
	
	public U getQuality();
	
	@Override
	public default int compareTo(ComparablePair<T,U> other) {
		return getQuality().compareTo(other.getQuality());
	}

}

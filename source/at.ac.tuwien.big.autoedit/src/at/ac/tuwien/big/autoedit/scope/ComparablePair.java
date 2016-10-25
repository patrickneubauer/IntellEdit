package at.ac.tuwien.big.autoedit.scope;

import java.util.Map.Entry;

import at.ac.tuwien.big.autoedit.transfer.ETransferrable;

public interface ComparablePair<T,U extends Comparable<U>> extends Comparable<ComparablePair<T,U>>, ETransferrable {
	
	public T getSolution();
	
	public U getQuality();
	
	@Override
	public default int compareTo(ComparablePair<T,U> other) {
		return getQuality().compareTo(other.getQuality());
	}

}

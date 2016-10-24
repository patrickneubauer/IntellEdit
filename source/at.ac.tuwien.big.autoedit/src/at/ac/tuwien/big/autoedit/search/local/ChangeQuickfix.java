package at.ac.tuwien.big.autoedit.search.local;

import at.ac.tuwien.big.autoedit.change.Change;

public interface ChangeQuickfix<T extends Change<T>, U extends Comparable<U>> extends Comparable<ChangeQuickfix<T, U>> {
	
	public void setQuality(U quality);
	
	public U getQuality();
	
	public default int compareTo(U u) {
		return getQuality().compareTo(u);
	}
	
	@Override
	public default int compareTo(ChangeQuickfix<T, U> o) {
		return getQuality().compareTo(o.getQuality());
	}
	
	public T getChange();
	
	public default String getDescription() {
		return String.valueOf(getChange());
	}

}

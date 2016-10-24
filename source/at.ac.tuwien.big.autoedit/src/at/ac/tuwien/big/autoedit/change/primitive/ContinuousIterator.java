package at.ac.tuwien.big.autoedit.change.primitive;

import java.util.Iterator;

public interface ContinuousIterator<T> extends Iterator<T> {
	
	public T next();
	
	public default boolean hasNext() {
		return true;
	}

}

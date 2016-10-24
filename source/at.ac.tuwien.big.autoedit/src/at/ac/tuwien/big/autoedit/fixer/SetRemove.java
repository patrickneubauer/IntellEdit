package at.ac.tuwien.big.autoedit.fixer;

import java.util.Collection;

public interface SetRemove extends FixAttempt {
	
	/**The object to be deleted*/
	public Object border();

	@Override
	public default boolean isFulfilled(Object obj) {
		return obj == null || ((obj instanceof Collection) && !((Collection)obj).contains(obj));
	}
}

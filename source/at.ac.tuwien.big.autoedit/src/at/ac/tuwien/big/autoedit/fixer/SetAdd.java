package at.ac.tuwien.big.autoedit.fixer;

import java.util.Collection;
import java.util.Objects;

public interface SetAdd extends FixAttempt {
	
	/**The Object which should be added*/
	public Object border();

	@Override
	public default boolean isFulfilled(Object obj) {
		return Objects.equals(obj, border()) || ((obj instanceof Collection) && ((Collection)obj).contains(obj));
	}
}

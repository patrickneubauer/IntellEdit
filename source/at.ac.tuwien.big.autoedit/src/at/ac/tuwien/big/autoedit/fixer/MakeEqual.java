package at.ac.tuwien.big.autoedit.fixer;

import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.impl.MakeEqualImpl;

public interface MakeEqual extends FixAttempt {
	
	public static final MakeEqual MAKENULL = new MakeEqualImpl(null);

	public Object border();


	@Override
	public default boolean isFulfilled(Object obj) {
		return Objects.equals(obj, border());
	}
}

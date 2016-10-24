package at.ac.tuwien.big.autoedit.fixer;

import at.ac.tuwien.big.autoedit.fixer.impl.MakeFalseImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeTrueImpl;

public interface MakeFalse extends FixAttempt {

	FixAttempt INSTANCE = MakeFalseImpl.INSTANCE;


	@Override
	public default boolean isFulfilled(Object obj) {
		return (obj instanceof Boolean) && !((Boolean)obj);
	}
}

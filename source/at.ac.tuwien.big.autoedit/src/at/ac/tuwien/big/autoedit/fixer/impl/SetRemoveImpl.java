package at.ac.tuwien.big.autoedit.fixer.impl;

import java.util.Collection;
import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.SetRemove;

public class SetRemoveImpl extends BorderFixer<SetRemoveImpl> implements SetRemove {

	public SetRemoveImpl(Object border) {
		super(border);
	}

	@Override
	public boolean subEquals(SetRemoveImpl other) {
		return true;
	}

	@Override
	public int subHashCode() {
		return 0;
	}
	

	public String toString() {
		return "Remove object "+border();
	}


}

package at.ac.tuwien.big.autoedit.fixer.impl;

import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeDifferent;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;

public class MakeDifferentlImpl extends BorderFixer<MakeDifferentlImpl> implements MakeDifferent {

	public MakeDifferentlImpl(Object border) {
		super(border);
	}
	

	@Override
	public boolean subEquals(MakeDifferentlImpl other) {
		return true;
	}

	@Override
	public int subHashCode() {
		return 0;
	}


	public String toString() {
		return "Make different from "+border();
	}

}

package at.ac.tuwien.big.autoedit.fixer.impl;

import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;

public class MakeEqualImpl extends BorderFixer<MakeEqualImpl> implements MakeEqual {

	public MakeEqualImpl(Object border) {
		super(border);;
	}
	

	@Override
	public boolean subEquals(MakeEqualImpl other) {
		return true;
	}

	@Override
	public int subHashCode() {
		return 0;
	}
	
	public String toString() {
		return "Make equal to "+border();
	}



}

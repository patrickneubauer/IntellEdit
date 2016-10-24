package at.ac.tuwien.big.autoedit.fixer.impl;

import at.ac.tuwien.big.autoedit.fixer.MakeFalse;

public class MakeFalseImpl implements MakeFalse {

	public static MakeFalseImpl INSTANCE = new MakeFalseImpl();
	
	public boolean equals(Object other) {
		return other instanceof MakeFalseImpl;
	}
	
	public int hashCode() {
		return 384637853;
	}
	
	public String toString() {
		return "Make false";
	}

}

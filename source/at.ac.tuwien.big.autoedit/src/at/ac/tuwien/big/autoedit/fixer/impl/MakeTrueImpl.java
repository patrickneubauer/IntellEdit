package at.ac.tuwien.big.autoedit.fixer.impl;

import at.ac.tuwien.big.autoedit.fixer.MakeTrue;

public class MakeTrueImpl implements MakeTrue {

	public static MakeTrueImpl INSTANCE = new MakeTrueImpl();
	
	public boolean equals(Object other) {
		return other instanceof MakeTrueImpl;
	}
	
	public int hashCode() {
		return 384647853;
	}
	
	public String toString() {
		return "Make true";
	}


}

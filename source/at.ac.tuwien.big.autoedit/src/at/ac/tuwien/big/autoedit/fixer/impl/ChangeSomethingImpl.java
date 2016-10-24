package at.ac.tuwien.big.autoedit.fixer.impl;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.fixer.ChangeSomething;

public class ChangeSomethingImpl implements ChangeSomething {
	
	public static final ChangeSomething INSTANCE = new ChangeSomethingImpl();
	
	public String toString() {
		return "Change something";
	}

}

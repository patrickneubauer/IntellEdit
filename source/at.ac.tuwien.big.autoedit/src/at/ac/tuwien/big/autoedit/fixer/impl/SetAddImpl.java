package at.ac.tuwien.big.autoedit.fixer.impl;

import java.util.Collection;
import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;

public class SetAddImpl extends BorderFixer<SetAddImpl>  implements SetAdd {

	public SetAddImpl(Object border) {
		super(border);
	}

	@Override
	public boolean subEquals(SetAddImpl other) {
		return true;
	}

	@Override
	public int subHashCode() {
		return 0;
	}

	public String toString() {
		return "Add object "+border();
	}


	

}

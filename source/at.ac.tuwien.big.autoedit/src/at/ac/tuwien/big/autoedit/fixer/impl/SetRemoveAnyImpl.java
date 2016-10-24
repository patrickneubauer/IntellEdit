package at.ac.tuwien.big.autoedit.fixer.impl;

import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;

import java.util.Collection;
import java.util.Objects;

import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;

public class SetRemoveAnyImpl implements SetRemoveAny {
	
	private int targetSize;

	public SetRemoveAnyImpl(int targetSize) {
		this.targetSize = targetSize;
	}

	public boolean equals(Object other) {
		return (other instanceof SetRemoveAny) && equals((SetRemoveAny)other);
	}
	
	public boolean equals(SetRemoveAny other) {
		return targetSize == other.targetSize();
	}

	@Override
	public int targetSize() {
		return targetSize;
	}
	

	public String toString() {
		return "Remove until size of " + targetSize;
	}


}

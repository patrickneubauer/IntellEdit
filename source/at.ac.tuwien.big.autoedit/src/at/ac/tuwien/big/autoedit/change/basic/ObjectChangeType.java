package at.ac.tuwien.big.autoedit.change.basic;

import at.ac.tuwien.big.autoedit.change.BasicChangeType;

public interface ObjectChangeType<Self extends ObjectChangeType<Self,BC>,BC extends ObjectChange<BC>> extends 
	BasicChangeType<Self,BC> {

}

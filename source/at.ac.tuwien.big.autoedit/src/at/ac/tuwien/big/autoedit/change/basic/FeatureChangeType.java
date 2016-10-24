package at.ac.tuwien.big.autoedit.change.basic;

import at.ac.tuwien.big.autoedit.change.BasicChangeType;

public interface FeatureChangeType<Self extends FeatureChangeType<Self,BC>, BC extends FeatureChange<BC>> 
	extends BasicChangeType<Self,BC> {

}

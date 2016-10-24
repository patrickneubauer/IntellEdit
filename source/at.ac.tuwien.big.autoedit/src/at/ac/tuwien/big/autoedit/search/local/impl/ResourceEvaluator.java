package at.ac.tuwien.big.autoedit.search.local.impl;

import at.ac.tuwien.big.autoedit.change.Change;

public interface ResourceEvaluator<T> {
	
	
	public double[] evaluate(Change<?> ch, T ref);

	
	public static ResourceEvaluator DEFAULT = new ViolatedConstraintsEvaluator();
}

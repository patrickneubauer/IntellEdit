package at.ac.tuwien.big.autoedit.evaluate.impl;

import org.eclipse.ocl.expressions.OCLExpression;

import at.ac.tuwien.big.autoedit.evaluate.Evaluable;
import at.ac.tuwien.big.autoedit.evaluate.EvaluationState;

public interface EvaluableReference<T extends EvaluationState<T>> {
	
	public Evaluable<?,T> getEvaluable();

}

package at.ac.tuwien.big.autoedit.evaluate.impl;

import java.util.Objects;

import org.eclipse.ocl.expressions.OCLExpression;

import at.ac.tuwien.big.autoedit.evaluate.Evaluable;
import at.ac.tuwien.big.autoedit.evaluate.EvaluationState;


public class EvaluableReferenceImpl<T extends EvaluationState<T>> implements EvaluableReference<T> {
	
	private Evaluable<?,T> evaluable;
	
	public EvaluableReferenceImpl(Evaluable<?,T> evaluable) {
		this.evaluable = evaluable;
	}

	@Override
	public Evaluable<?,T> getEvaluable() {
		return evaluable;
	}
	
	public boolean equals(Object other) {
		return (other instanceof EvaluableReferenceImpl) && Objects.equals(getEvaluable(),((EvaluableReference)other).getEvaluable());
	}
	
	public int hashCode() {
		return Objects.hashCode(evaluable);
	}

	public String toString() {
		return String.valueOf(evaluable);
	}
}

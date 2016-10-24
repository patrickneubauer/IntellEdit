package at.ac.tuwien.big.oclgen;

import org.eclipse.emf.ecore.EStructuralFeature;

public interface OCLBooleanExpressionEvaluator<T> {

	boolean isValid(T self);
	
	EStructuralFeature findErrorFeature(T self);
	
}

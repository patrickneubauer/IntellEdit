package at.ac.tuwien.big.autoedit.evaluate.impl;

import java.util.Objects;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.evaluate.Evaluable;
import at.ac.tuwien.big.autoedit.evaluate.EvaluationState;
import at.ac.tuwien.big.autoedit.transfer.ETransferrable;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class OCLExpressionEvaluable implements Evaluable {
	
	private OCLExpression expr;
	
	public OCLExpression getExpression() {
		return expr;
	}
	
	public OCLExpressionEvaluable(OCLExpression expr) {
		this.expr = expr;
	}

	@Override
	public EvaluationState getState(MyResource res, EObject obj) {
		return new OCLExpressionEvaluationState(res,this,obj);
	}
	
	public boolean equals(Object o) {
		return (o instanceof OCLExpressionEvaluable) && Objects.equals(expr, ((OCLExpressionEvaluable)o).expr);
	}

	public int hashCode() {
		return Objects.hashCode(expr);
	}

	@Override
	public OCLExpressionEvaluable clone() {
		return new OCLExpressionEvaluable(expr);
	}
	
	public String toString() {
		return String.valueOf(expr);
	}
}

package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.VariableExp;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;
import at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators.PropagateBooleanChanges.OCLBooleanState;

public class SwallowSelfChange extends AbstractSelectiveEvaluator<VariableExp, Object> implements FixingGenerator<VariableExp, Object> {

	public SwallowSelfChange() {
		super(VariableExp.class, null, false, null);
		// TODO Auto-generated constructor stub
	}
	
	public static SwallowSelfChange INSTANCE = new SwallowSelfChange();
	
	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, VariableExp expr,
			Object result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		if (expr.getReferredVariable() != null && "self".equals(expr.getReferredVariable().getName())) {
			return true;
		}
		return false;
	}


}

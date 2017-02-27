package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.time.format.SignStyle;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.ocl.ecore.IfExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeFalseImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeTrueImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;
import at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators.PropagateBooleanChanges.OCLBooleanState;

public class PropagateIfChanges   extends AbstractSelectiveEvaluator<IfExp, Object> implements FixingGenerator<IfExp, Object> {
	
	public PropagateIfChanges() {
		super(IfExp.class, Object.class, true,null);
	}
	
	/**Seltsamerweise sind die Fixes bei beiden dieselben, auch wenn es unterschiedlich ist, was alles
	 * erfüllt werden muss
	 */
	
	public static final PropagateIfChanges INSTANCE = new PropagateIfChanges();

	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, IfExp expr,
			Object result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		OCLBooleanState bs = res.getResultAsState();
		if (bs == OCLBooleanState.NULL) {
			fixAttemptsPerSub[0].add(MakeTrueImpl.INSTANCE);
			fixAttemptsPerSub[0].add(MakeFalseImpl.INSTANCE);
		} else if (bs == OCLBooleanState.TRUE){
			fixAttemptsPerSub[0].add(MakeFalseImpl.INSTANCE);
		} else if (bs == OCLBooleanState.FALSE) {
			//System.out.println("faps before: " + fixAttemptsPerSub[0]);
			fixAttemptsPerSub[0].add(MakeTrueImpl.INSTANCE);
			//FixAttempt makeTrue = MakeTrueImpl.INSTANCE;
			//System.out.println("faps after: " + fixAttemptsPerSub[0]);
			//System.out.println("MakeTrue: "+MakeTrueImpl.INSTANCE);
		} else {
			return false;
		}
		
		for (int i = 1; i < fixAttemptsPerSub.length; ++i) {
			fixAttemptsPerSub[i].add(singleAttemptForThis);
		}
		return true;
	}

}

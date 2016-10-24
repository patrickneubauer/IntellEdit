package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;

import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeDifferent;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeEqualImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class PropagateSumChanges  extends AbstractSelectiveEvaluator<OperationCallExp, Number> implements FixingGenerator<OperationCallExp, Number> {

	public PropagateSumChanges() {
		super(OperationCallExp.class, Number.class, true, "sum");
	}

	public static PropagateSumChanges INSTANCE = new PropagateSumChanges();
	
	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, OperationCallExp expr,
			Number result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		if (!(singleAttemptForThis instanceof MakeEqual ||
				singleAttemptForThis instanceof MakeDifferent ||
				singleAttemptForThis instanceof Increase ||
				singleAttemptForThis instanceof Decrease)) {
			return false;
		}
		if (singleAttemptForThis instanceof MakeDifferent || singleAttemptForThis instanceof Increase
				|| singleAttemptForThis instanceof Decrease || singleAttemptForThis instanceof MakeEqual) {
			//Do anything
			fixAttemptsPerSub[0].add(singleAttemptForThis);
			//Eigentlich sollte man schauen, woher die Collection kommt
			//Und dann
		}
		// TODO Auto-generated method stub
		return true;
	}

}

package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;

import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeDifferent;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeConformRegexpImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.MakeEqualImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class FixRegexpChanges  extends AbstractSelectiveEvaluator<OperationCallExp, Boolean> implements FixingGenerator<OperationCallExp, Boolean> {

	public FixRegexpChanges() {
		super(OperationCallExp.class, Boolean.class, true, "matches");
	}

	public static FixRegexpChanges INSTANCE = new FixRegexpChanges();
	
	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, OperationCallExp expr,
			Boolean result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		if (!(singleAttemptForThis instanceof MakeEqual ||
				singleAttemptForThis instanceof MakeTrue)) {
			return false;
		}
		if (singleAttemptForThis instanceof MakeEqual) {
			MakeEqual eq = (MakeEqual)singleAttemptForThis;
			if (!eq.isLooseMakeTrue()) {
				return false;
			}
		}
		
		
		
		//Do anything
		fixAttemptsPerSub[0].add(new MakeConformRegexpImpl((String)res.getSubResultValue(1)));
		//Eigentlich sollte man schauen, woher die Collection kommt
		return true;
	}

}

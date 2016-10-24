package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.fixer.ChangeSomething;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.impl.ChangeSomethingImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class PropagateUnknownChange extends AbstractSelectiveEvaluator<Object, Object> implements FixingGenerator<Object, Object>{

	public PropagateUnknownChange() {
		super(Object.class, Object.class, true, null);
	}

	public static final PropagateUnknownChange INSTANCE = new PropagateUnknownChange();	
	
	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, Object expr,
			Object result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		if (singleAttemptForThis instanceof ChangeSomething) { 
			for (int i = 0; i < fixAttemptsPerSub.length; ++i) {
				fixAttemptsPerSub[i].add(ChangeSomethingImpl.INSTANCE);
			}
			return true;
		}
		return false;
	}

}

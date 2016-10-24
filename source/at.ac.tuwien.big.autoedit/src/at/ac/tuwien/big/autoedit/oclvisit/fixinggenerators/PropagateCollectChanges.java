package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;

import org.eclipse.ocl.ecore.IterateExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.fixer.impl.ChangeSomethingImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetAddAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveAnyImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class PropagateCollectChanges   extends AbstractSelectiveEvaluator<IteratorExp, Object> 
	implements FixingGenerator<IteratorExp, Object>  {


	public PropagateCollectChanges() {
		super(IteratorExp.class, null, true, "collect");
	}

	public static PropagateCollectChanges INSTANCE = new PropagateCollectChanges();
	
	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, IteratorExp expr,
			Object result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		if (singleAttemptForThis instanceof SetRemove || singleAttemptForThis instanceof SetRemoveAny
				 || singleAttemptForThis instanceof SetAdd || singleAttemptForThis instanceof SetAddAny) {
			if (singleAttemptForThis instanceof SetAddAny) {
				for (int i = 0; i < fixAttemptsPerSub.length; ++i) {
					fixAttemptsPerSub[i].add(new SetAddAnyImpl(Integer.MAX_VALUE));
				}
			} else if (singleAttemptForThis instanceof SetRemoveAny) {
				for (int i = 0; i < fixAttemptsPerSub.length; ++i) {
					fixAttemptsPerSub[i].add(new SetRemoveAnyImpl(0));
				}
			}  else if (singleAttemptForThis instanceof SetAdd || singleAttemptForThis instanceof SetRemove) {
				if (singleAttemptForThis instanceof SetAdd) {
					fixAttemptsPerSub[0].add(new SetAddAnyImpl(0));	
				} else {
					fixAttemptsPerSub[0].add(new SetRemoveAnyImpl(0));
				}
				for (int i = 1; i < fixAttemptsPerSub.length; ++i) {
					fixAttemptsPerSub[i].add(singleAttemptForThis);
				}
			}
			return true;
		}
		if (singleAttemptForThis instanceof Increase || singleAttemptForThis instanceof Decrease) {
			fixAttemptsPerSub[0].add(new ChangeSomethingImpl());
			for (int i = 1; i < fixAttemptsPerSub.length; ++i) {
				//TODO: Es sollte relativ sein
				fixAttemptsPerSub[i].add(singleAttemptForThis);
			}
		}
		return false;
	}

}

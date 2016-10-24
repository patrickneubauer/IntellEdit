package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;
import java.util.Objects;

import org.eclipse.ocl.expressions.IteratorExp;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class SelectMoreOrLessChange  extends AbstractSelectiveEvaluator<IteratorExp, Object> implements FixingGenerator<IteratorExp, Object> {

	public SelectMoreOrLessChange() {
		super(IteratorExp.class, null, false, "select");
	}
	
	public static SelectMoreOrLessChange INSTANCE = new SelectMoreOrLessChange();

	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, IteratorExp expr,
			Object result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		
		Collection sourceValue = res.getSourceResultAsCollection();
		if ((singleAttemptForThis instanceof SetAdd) || (singleAttemptForThis instanceof SetRemove)) {
			Object obj = (singleAttemptForThis instanceof SetAdd)?(((SetAdd)singleAttemptForThis).border()):
				(((SetRemove)singleAttemptForThis).border());
			//if it is in 
			boolean colcont = sourceValue.contains(obj);
			boolean shouldcont = (singleAttemptForThis instanceof SetAdd);
			if (colcont) { //It is there, but the value is incorrect 
				int ind = 0;
				for (Object o: sourceValue) {
					if (Objects.equals(obj,o)) {
						fixAttemptsPerSub[ind].add(shouldcont?MakeTrue.INSTANCE:MakeFalse.INSTANCE);
					}
					++ind;
				}
				if (!shouldcont) { //Another alternative: Delete it from the source
					fixAttemptsPerSub[0].add(singleAttemptForThis);
				}
			} else if (!colcont && shouldcont) { //It is not even there yet, so add it ...
				fixAttemptsPerSub[0].add(singleAttemptForThis);
				return true;
			} else {
				//Don't know what to do
				return false;
			}
		} else if (singleAttemptForThis instanceof SetAddAny) {
			//Make more true
			for (int i = 1; i < fixAttemptsPerSub.length; ++i) {
				fixAttemptsPerSub[i].add(MakeTrue.INSTANCE);
			}
			//Add more elements
			fixAttemptsPerSub[0].add(singleAttemptForThis);
			return true;
		} else if (singleAttemptForThis instanceof SetRemoveAny) {
			//Make more false
			for (int i = 1; i < fixAttemptsPerSub.length; ++i) {
				fixAttemptsPerSub[i].add(MakeFalse.INSTANCE);
			}
			//Remove elements
			fixAttemptsPerSub[0].add(singleAttemptForThis);
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

}

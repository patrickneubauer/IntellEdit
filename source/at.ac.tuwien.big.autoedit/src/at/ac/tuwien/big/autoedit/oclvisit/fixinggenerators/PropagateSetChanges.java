package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.fixer.impl.SetAddAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetAddImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;

public class PropagateSetChanges extends AbstractSelectiveEvaluator<OperationCallExp, Collection> implements FixingGenerator<OperationCallExp, Collection>{

	private enum Type {
		UNION("union"),INTERSECTION("intersect"),MINUS("-");
		
		private String oclName;
		
		private Type(String oclName) {
			this.oclName = oclName;
		}
	}
	
	private Type type;
	
	public PropagateSetChanges(Type type) {
		super(OperationCallExp.class, Collection.class, false, type.oclName);
		this.type = type;
	}
	
	public static PropagateSetChanges UNION = new PropagateSetChanges(Type.UNION);
	public static PropagateSetChanges INTERSECTION = new PropagateSetChanges(Type.INTERSECTION);
	public static PropagateSetChanges MINUS = new PropagateSetChanges(Type.MINUS);
	

	@Override
	public boolean addFixingPossibilitiesLocal(FixAttempt singleAttemptForThis, ExpressionResult res, OperationCallExp expr,
			Collection result, Collection<FixAttempt>[] fixAttemptsPerSub) {
		
		//You can just propagate everything, even though e.g. for SetAddAny things will not be correlated ...
		if (!(singleAttemptForThis instanceof SetAdd || singleAttemptForThis instanceof SetRemove
				|| singleAttemptForThis instanceof SetAddAny || singleAttemptForThis instanceof SetRemoveAny)) {
			return false;
		}
		
		if (type == Type.UNION || type == Type.INTERSECTION) {
			for (int i = 0; i < fixAttemptsPerSub.length; ++i) {
				fixAttemptsPerSub[i].add(singleAttemptForThis);
			}
		} else if (type == Type.MINUS) {
			fixAttemptsPerSub[0].add(singleAttemptForThis);
			if (singleAttemptForThis instanceof SetAdd) {
				fixAttemptsPerSub[1].add(new SetRemoveImpl(((SetAdd) singleAttemptForThis).border()));
			} else if (singleAttemptForThis instanceof SetRemove) {
				fixAttemptsPerSub[1].add(new SetAddImpl(((SetRemove) singleAttemptForThis).border()));
			} else if (singleAttemptForThis instanceof SetAddAny) {
				fixAttemptsPerSub[1].add(new SetRemoveAnyImpl(0));				
			} else if (singleAttemptForThis instanceof SetRemoveAny) {
				fixAttemptsPerSub[1].add(new SetAddAnyImpl(Integer.MAX_VALUE));
			}
		}
		if (type == Type.UNION) {
			//Bei RemoveAny versuche ganz konkret Sachen zu löschen, die in nur einem vorkommen
			if (singleAttemptForThis instanceof SetRemoveAny) {
				Set firstSet = new HashSet<>(res.getSourceResultAsCollection());
				Set secondSet = new HashSet<>(res.getResultAsCollection(1));
				firstSet.removeAll(secondSet);
				secondSet.removeAll(res.getSourceResultAsCollection());
				for (Object firstO: firstSet) {
					fixAttemptsPerSub[0].add(new SetRemoveImpl(firstO));
				}
				for (Object secondO: secondSet) {
					fixAttemptsPerSub[1].add(new SetRemoveImpl(secondO));
				}
			}
		} else if (type == Type.INTERSECTION) {
			//Bei AddAny versuche ganz konkret Sachen hinzuzufügen, die im anderen vorkommen
			if (singleAttemptForThis instanceof SetAddAny) {
				Set firstSet = new HashSet<>(res.getSourceResultAsCollection());
				Set secondSet = new HashSet<>(res.getResultAsCollection(1));
				firstSet.removeAll(secondSet);
				secondSet.removeAll(res.getSourceResultAsCollection());
				for (Object firstO: firstSet) {
					fixAttemptsPerSub[1].add(new SetAddImpl(firstO));
				}
				for (Object secondO: secondSet) {
					fixAttemptsPerSub[0].add(new SetAddImpl(secondO));
				}
			}
		} else if (type == Type.MINUS) {
			//Bei RemoveAny versuche ganz konkret hinten Sachen hinzuzufügen, die vorne stehen
			if (singleAttemptForThis instanceof SetRemoveAny) {
				for (Object firstO: res.getSourceResultAsCollection()) {
					fixAttemptsPerSub[1].add(new SetAddImpl(firstO));	
				}
				
			}
		}
		return true;
	}

}

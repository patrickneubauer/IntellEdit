package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.ocl.ecore.IterateExp;
import org.eclipse.ocl.ecore.PropertyCallExp;
import org.eclipse.ocl.expressions.IteratorExp;
import org.eclipse.ocl.expressions.OperationCallExp;

import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.fixer.impl.ChangeSomethingImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetAddAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveImpl;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
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
				boolean propertySuccess = false;
				{
					if (singleAttemptForThis instanceof SetRemove) {
						//Remove property giving that
						SetRemove sr = (SetRemove)singleAttemptForThis;
						Map<Object,Set<Object>> relevantProperties = new HashMap<>();
						EvalResult source = res.getSubResults().get(0);
						if (source.getResult() instanceof Collection) {
							List<Object> baseSource = new ArrayList<>((Collection)source.getResult());
							if (baseSource.size() != res.getSubResults().size()-1) {
								System.err.println("This doesn't work like I want");
							} else {
								for (int i = 1; i < res.getSubResults().size(); ++i) {
									Object sresult = res.getSubResults().get(i).getResult();
									Set<Object> cur = relevantProperties.get(sresult);
									if (cur == null) {
										relevantProperties.put(sresult, cur = new HashSet<Object>());
									}
									cur.add(baseSource.get(i));
								}
								Set<Object> relevant = relevantProperties.get(sr.border());
								if (relevant != null && !relevant.isEmpty()) {
									for (Object o: relevant) {
										fixAttemptsPerSub[0].add(new SetRemoveImpl(o));
									}
									propertySuccess = true;
								}
							}
						}
						  
						
					}
				} 
				if (!propertySuccess) {
					if (singleAttemptForThis instanceof SetAdd) {
						fixAttemptsPerSub[0].add(new SetAddAnyImpl(0));	
					} else {
						fixAttemptsPerSub[0].add(new SetRemoveAnyImpl(0));
					}
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
			return true;
		}
		return false;
	}

}

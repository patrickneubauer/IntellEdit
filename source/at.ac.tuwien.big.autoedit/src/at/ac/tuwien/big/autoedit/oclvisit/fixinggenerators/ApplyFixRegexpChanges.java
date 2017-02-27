package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;

import com.google.common.base.Objects;

import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.change.basic.BasicSetConstantChange;
import at.ac.tuwien.big.autoedit.change.basic.FixedClearChangeType;
import at.ac.tuwien.big.autoedit.change.basic.FixedReplaceConstantChangeType;
import at.ac.tuwien.big.autoedit.change.basic.FixedSetConstantChangeType;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.fixer.Decrease;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.MakeConformRegexp;
import at.ac.tuwien.big.autoedit.fixer.MakeEqual;
import at.ac.tuwien.big.autoedit.fixer.MakeFalse;
import at.ac.tuwien.big.autoedit.fixer.MakeTrue;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixActionMap;
import at.ac.tuwien.big.autoedit.oclvisit.FixingActionGenerator;
import at.ac.tuwien.big.autoedit.oclvisit.FixingGenerator;
import at.ac.tuwien.big.simpleregexp.RegExpAlgorithm;
import at.ac.tuwien.big.xtext.util.IteratorUtils;
import at.ac.tuwien.big.xtext.util.MyEcoreUtil;
import dk.brics.automaton.Automaton;

public class ApplyFixRegexpChanges  extends AbstractSelectiveEvaluator<PropertyCallExp, Object> implements FixingActionGenerator<PropertyCallExp, Object> {

	public ApplyFixRegexpChanges() {
		super(PropertyCallExp.class, Object.class, true, null);
	}
	
	public static ApplyFixRegexpChanges INSTANCE = new ApplyFixRegexpChanges();
	
	public static Collection wrapCollection(Object objOrCollection) {
		if (objOrCollection == null) {
			return Collections.emptyList();
		}
		if (objOrCollection instanceof Collection) {
			return (Collection)objOrCollection;
		}
		return Collections.singleton(objOrCollection);
	}

	@Override
	public boolean addFixingPossibilitiesLocal(MyResource resource, FixAttempt singleAttemptForThis, EvalResult baseres, int priority, FixActionMap potentialFixChanges) {
		if (!(baseres instanceof ExpressionResult)) {
			return false;
		}
		if (!(singleAttemptForThis instanceof MakeConformRegexp)) {
			return false;
		}
		Automaton regexp = ((MakeConformRegexp)singleAttemptForThis).getAutomaton();
		ExpressionResult res = (ExpressionResult)baseres;
		PropertyCallExp expr = (PropertyCallExp) res.getExpression();
		
		//Increase, Decrease, SetAdd, SetRemove ... everything should/could be handled here
		
		if (!(expr.getReferredProperty() instanceof EStructuralFeature)) {
			System.err.println("Strange referrend property " + expr.getReferredProperty());
			return false;
		}
		
		if (res.getSubResults().size() != 1) {
			System.err.println("Wrong result size for PropertyExp: " + res.getSubResults().size());
			return false;
		}
		
		Object src = res.getSubResultValue(0);
		Collection srcObjs = wrapCollection(src);
		EStructuralFeature targetFeat = (EStructuralFeature)expr.getReferredProperty();
		EClassifier targetType = targetFeat.getEType();
		RegExpAlgorithm algo = new RegExpAlgorithm((String)baseres.getResult(), regexp);
		//TODO: Es gibt auch andere Fix-Möglichkeiten, z.B. die Source-Collection entsprechend zu ändern, 
		//so dass das passende rauskommt
		//So sicher nicht, das ist dann vielleicht der konkrete Fix ...
		for (Object srcObj: srcObjs) {
			EObject obj = (EObject)srcObj;
			
			if (FeatureMapUtil.isMany(obj, targetFeat)) {
				int index = 0;
				for (Object o: MyEcoreUtil.getAsCollection(obj, targetFeat)) {
					if (Objects.equal(o, src)) {
						potentialFixChanges.addFixingAction(priority, new FixedReplaceConstantChangeType<>(resource.getResource(),obj,targetFeat,
								index,
								ParameterType.equalProbability(String.class,IteratorUtils.buildList(algo.getMainIteratorString(),100))));		
					}
					++index;
				}
				return true;
			} else {
				potentialFixChanges.addFixingAction(priority, new FixedSetConstantChangeType<>(resource.getResource(),obj,targetFeat,
						ParameterType.equalProbability(String.class,IteratorUtils.buildList(algo.getMainIteratorString(),100))));
			}
		}
		return true;
	}

	@Override
	public int getBasePriority() {
		return FixingActionGenerator.DIRECT_FIX_PRIORITY;
	}

}

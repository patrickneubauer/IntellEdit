package at.ac.tuwien.big.autoedit.evaluate.impl;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.change.basic.FixedAddConstantChangeType;
import at.ac.tuwien.big.autoedit.change.basic.FixedDeleteConstantChangeType;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.evaluate.EvaluationState;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.impl.SetAddAnyImpl;
import at.ac.tuwien.big.autoedit.fixer.impl.SetRemoveAnyImpl;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptFeatureReferenceImpl;
import at.ac.tuwien.big.autoedit.oclvisit.FixingActionGenerator;
import at.ac.tuwien.big.autoedit.oclvisit.SimpleResult;
import at.ac.tuwien.big.autoedit.search.local.LocalSearchInterface;
import at.ac.tuwien.big.autoedit.search.local.LocalSearchManager;
import at.ac.tuwien.big.autoedit.search.local.NeighborhoodProvider;

public class MultiplicityEvaluationState implements EvaluationState<MultiplicityEvaluationState> {
	
	MultiplicityEvaluable evaluable;
	MyResource res;
	private EObject eobj;
	
	public MultiplicityEvaluationState(MyResource res, MultiplicityEvaluable eval, EObject eobj) {
		this.res = res;
		this.eobj = eobj;
		this.evaluable = eval;
	}

	@Override
	public void initParam() {
	}

	@Override
	public void reuseParam(MultiplicityEvaluationState from) {		
	}

	@Override
	public Object evaluateBasic() {
		EStructuralFeature feat  =evaluable.getFeature();
		int curSize = MyEcoreUtil.getAsCollectionSize(eobj, feat);
		if (curSize < feat.getLowerBound())  {
			return false;
		}
		if (feat.getUpperBound() >= 0 && curSize > feat.getUpperBound())  {
			return false;
		}
		return true;
	}
	
	private EvalResult result;
	private double quality;
	
	@Override
	public Object evaluateFull() {
		if (result == null) {
			
			EStructuralFeature feat  =evaluable.getFeature();
			int curSize = MyEcoreUtil.getAsCollectionSize(eobj, feat);
			if (curSize < feat.getLowerBound())  {
				result = new SimpleResult(feat);
				result.setResult(false);
				FixAttempt fix = new SetAddAnyImpl(feat.getLowerBound());
				result.addPossibleFix(fix);
				FixAttemptFeatureReferenceImpl fref = new FixAttemptFeatureReferenceImpl(eobj,feat);
				result.getFixAttemptReferenceInfo().addFixAttemptReference(fref, new EvaluableReferenceImpl<>(evaluable) ,fix); 
				ParameterType valueGeneratingParameter = res.defaultGenerator(feat);
				result.addPossibleFixingActions(Arrays.asList(
						new FixedAddConstantChangeType<>(res.getResource(), eobj, feat,
								valueGeneratingParameter)), FixingActionGenerator.DIRECT_FIX_PRIORITY);
				quality = ((double)curSize) / feat.getLowerBound();
				return false;
			}
			if (feat.getUpperBound() >= 0 && curSize > feat.getUpperBound())  {
				result = new SimpleResult(feat);
				result.setResult(false);
				FixAttempt fix = new SetRemoveAnyImpl(feat.getUpperBound());
				result.addPossibleFix(fix);
				FixAttemptFeatureReferenceImpl fref = new FixAttemptFeatureReferenceImpl(eobj,feat);
				result.getFixAttemptReferenceInfo().addFixAttemptReference(fref, new EvaluableReferenceImpl<>(evaluable) ,fix);
				result.addPossibleFixingActions(Arrays.asList(
						new FixedDeleteConstantChangeType(res.getResource(), eobj, feat)),
						FixingActionGenerator.DIRECT_FIX_PRIORITY);
				quality = 1.0/(1.0+curSize-feat.getUpperBound());
				return false;
			}
			quality = 1.0;
			result = new SimpleResult(feat);
			result.setResult(true);
		}
		return result.getResult();
	}

	@Override
	public EvalResult getResult() {
		if (result == null) {
			evaluateFull();
		}
		return result;
	}

	@Override
	public double getQuality() {
		if (result == null) {
			evaluateFull();
		}
		return quality;
	}


}

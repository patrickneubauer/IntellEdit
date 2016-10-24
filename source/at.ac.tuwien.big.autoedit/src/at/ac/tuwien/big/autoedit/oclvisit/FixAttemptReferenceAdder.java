package at.ac.tuwien.big.autoedit.oclvisit;

import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators.GetQualityFunc;

public interface FixAttemptReferenceAdder<T,U> extends SelectiveEvaluator<T,U> {

	public void addFixAttemptReferences(MyResource myres, FixAttempt singleAttemptForThis, EvalResult baseRes, ExpressionResult res, T expr, U result, FixAttemptReferenceInfo info, RejectingFilterManager man);
	
	
	//FixAttempt --> could handle
	public default void addFixAttemptReferences(MyResource myres, EvalResult res, RejectingFilterManager man) {
		for (FixAttempt at: res.getPossibleFixes()) {
			if (at.isFulfilled(res.getResult())) {
				continue;
			}
			if (res instanceof ExpressionResult) {
				ExpressionResult eres = (ExpressionResult)res;
				addFixAttemptReferences(myres,at, res, eres, (T)eres.getExpression(), (U)eres.getResult(), res.getFixAttemptReferenceInfo(), man);
			} else {
				addFixAttemptReferences(myres,at, res, null, null, null, res.getFixAttemptReferenceInfo(), man);
			}
		}
		
	}

}

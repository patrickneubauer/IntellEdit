package at.ac.tuwien.big.autoedit.evaluate;

import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;

public interface EvaluationCallback<T extends EvaluationState<T>> {
	
	public void callbackSuccess(Evaluable<?,T> evaluable, T state, Object result);
	
	public void callbackFailure(Evaluable<?,T> evaluable, T state, Object result, EvalResult res);

}

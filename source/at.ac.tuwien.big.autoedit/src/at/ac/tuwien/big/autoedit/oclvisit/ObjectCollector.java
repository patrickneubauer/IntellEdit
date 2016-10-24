package at.ac.tuwien.big.autoedit.oclvisit;

public interface ObjectCollector<T,U> extends SelectiveEvaluator<T, U> {

	public void collect(EvalResult result, ObjectCollection col);
}

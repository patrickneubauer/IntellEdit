package at.ac.tuwien.big.autoedit.evaluate;

import org.eclipse.emf.ecore.EObject;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.transfer.ETransferrable;

public interface Evaluable<U extends Evaluable<U,T>,T extends EvaluationState<T>> {
	
	public T getState(MyResource res, EObject obj);

}

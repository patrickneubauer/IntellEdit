package at.ac.tuwien.big.autoedit.change.primitive;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;

public interface ValueModificator<T> {
	
	/**Should NOT modify T, but provide a new object based on a modification of from*/
	public T modify(MyResource res, EObject obj, EStructuralFeature feat, T from);

}

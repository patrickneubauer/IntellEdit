package at.ac.tuwien.big.autoedit.transfer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public interface ETransferrable<Sub extends ETransferrable<Sub>> {
	
	public Sub clone();
	
	public void transfer(EcoreTransferFunction func);
	
	public default Sub clone(EcoreTransferFunction func) {
		Sub ret = clone();
		ret.transfer(func);
		return ret;
	}
	
	public static Resource transfer(Resource base, EcoreTransferFunction func) {
		return func.other(base);
	}
	
	public static EObject transfer(EObject base, EcoreTransferFunction func) {
		if (base == null) {return null;}
		EObject ret = func.forward((EObject)base);
		if (ret == base && base.eResource() != null && base.eResource().equals(func.backResource())) {
			//Create new
			ret = func.weakChangeResource(base);
		}
		return ret;
	}
	
	public static<T> Collection<T> transfer(Collection<T> base, EcoreTransferFunction func) {
		if (base instanceof List) {
			return transfer((List<T>)base,func);
		}
		Collection<T> toRemove = new ArrayList<>();
		Collection<T> toAdd = new ArrayList<>();
		Collection<T> col = (Collection<T>)base;
		for (T o: col) {
			Object newO = transfer(o,func);
			if (newO != o) {
				toRemove.add(o);
				toAdd.add((T)newO);
			}
		}
		col.removeAll(toRemove);
		col.addAll(toAdd);
		return base;
	}
	
	public static<T> List<T> transfer(List<T> base, EcoreTransferFunction func) {
		ListIterator<T> l = ((List<T>) base).listIterator();
		while (l.hasNext()) {
			Object o = l.next();
			Object newO = transfer(o,func);
			if (o != newO) {
				l.set((T)newO);
			}
		}
		return base;
	}
	
	public static<Sub extends ETransferrable<Sub>> Sub transfer(Sub base, EcoreTransferFunction func) {
		base.transfer(func);
		return base;
	}
	
	public static Object transfer(Object base, EcoreTransferFunction func) {
		if (base instanceof EObject) {
			return transfer((EObject)base,func);
		} else if (base instanceof Collection) {
			return transfer((Collection)base,func);
		} else if (base instanceof ETransferrable) {
			return transfer((ETransferrable)base,func);
		} else if (base instanceof Resource) {
			return transfer((Resource)base,func);
		} else {
			//Assert that it is OK
			return base;
		}
	}

}

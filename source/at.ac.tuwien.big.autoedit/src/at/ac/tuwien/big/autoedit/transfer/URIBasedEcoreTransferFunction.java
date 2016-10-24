package at.ac.tuwien.big.autoedit.transfer;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

public class URIBasedEcoreTransferFunction implements EcoreTransferFunction {
	
	private Resource start;
	private Resource target;
	
	public URIBasedEcoreTransferFunction(Resource start, Resource target) {
		this.start = start;
		this.target = target;
	}


	@Override
	public EObject forward(EObject from) {
		if (from == null || (from.eResource() != null && from.eResource().equals(target))) {
			return from;
		}
		if (from.eResource() == null) {
			return weakChangeResource(from, target);
		}
		try {
			EObject ret = target.getEObject(from.eResource().getURIFragment(from));
			if (ret == from) {
				ret = weakChangeResource(from, target);
			}
			return ret;
		} catch (IllegalArgumentException e) {
			EObject ret = weakChangeResource(from, target);
			return ret;
		}
		
	}

	@Override
	public EObject back(EObject from) {
		if (from == null || from.eResource().equals(start)) {
			return from;
		}
		if (from.eResource() == null) {
			return weakChangeResource(from, target);
		}
		EObject ret = start.getEObject(from.eResource().getURIFragment(from));
		if (ret == from) {
			ret = weakChangeResource(from, start);
		}
		return ret;
	}

	@Override
	public Resource forwardResource() {
		return target;
	}

	@Override
	public Resource backResource() {
		return start;
	}

	EcoreUtil.Copier copier = new EcoreUtil.Copier();
	private static MethodHandle eSetDirectResource = null;
	private static MethodHandle eBasicProperties = null;
	private static MethodHandle setEResource = null;
	static {
		try {
			Method eSetMethod = (MinimalEObjectImpl.class.getDeclaredMethod("eSetDirectResource",Resource.Internal.class));
			eSetMethod.setAccessible(true);
			eSetDirectResource = MethodHandles.lookup().unreflect(eSetMethod);
			Method eBasicPropertiesM = (EObjectImpl.class.getDeclaredMethod("eBasicProperties"));
			eBasicPropertiesM.setAccessible(true);
			eBasicProperties = MethodHandles.lookup().unreflect(eBasicPropertiesM);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WeakHashMap<EObject, EObject> reverseCopied = new WeakHashMap<EObject, EObject>(); 

	@Override
	public EObject weakChangeResource(EObject base, Resource targetR) {
		if (reverseCopied.get(base) != null) {
			return reverseCopied.get(base);
		}
		EObject otherObj = copier.copy(base);
		if (otherObj instanceof MinimalEObjectImpl) {
			MinimalEObjectImpl eobj = (MinimalEObjectImpl)otherObj;
			try {
				eSetDirectResource.invoke(eobj,targetR);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		} else if (otherObj instanceof EObjectImpl) {
			EObjectImpl eobj = (EObjectImpl)otherObj;
			try {
				Object basicProperties = eBasicProperties.invoke(eobj);
				if (setEResource == null) {
					Class<?> cl = basicProperties.getClass();
					while (setEResource == null) {
						 Method eSetResourceM = cl.getDeclaredMethod("setEResource",Resource.Internal.class);
						 if (eSetResourceM != null) {
							 eSetResourceM.setAccessible(true);
							 setEResource = MethodHandles.lookup().unreflect(eSetResourceM);
						 }
					}
					setEResource.invoke(basicProperties,targetR);
				}
			} catch(Throwable t) {
				System.err.println("ERROR");
			}
		} else {
			System.err.println("Unknown EObject type " + otherObj);
		}
		
		return otherObj;
	}

}

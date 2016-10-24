package at.ac.tuwien.big.autoedit.change.basic;

import java.math.BigInteger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.change.parameter.SimpleParameter;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;

public class FixedSetConstantChangeType<T> extends AbstractFixedFeatureChangeType<FixedSetConstantChangeType<T>,
	BasicSetConstantChange> {
	
	public FixedSetConstantChangeType(Resource res, EObject eobj, EStructuralFeature feat, 
				ParameterType<FixedSetConstantChangeType<T>, T> par) {
		super(res,eobj,feat,SimpleParameter.fromType(par, "value", 0));
	}

	

	protected FixedSetConstantChangeType(FixedSetConstantChangeType<T> other) {
		super(other);
	}

	@Override
	public BasicSetConstantChange compile() {
		return new BasicSetConstantChange(getResource(),getEObject(), getFeature(), get(0));
	}

	@Override
	public FixedSetConstantChangeType<T> clone() {
		return new FixedSetConstantChangeType<T>(this);
	}

	@Override
	protected String getShortName() {
		return "Set";
	}

}

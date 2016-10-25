package at.ac.tuwien.big.autoedit.change.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import at.ac.tuwien.big.autoedit.change.BasicChange;
import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.CostProvider.CostFunction;
import at.ac.tuwien.big.autoedit.change.EObjectChangeMap;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptFeatureReferenceImpl;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReference;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class AddConstantChange extends AbstractFeatureChange<AddConstantChange> implements FeatureChange<AddConstantChange> {

	private Object value;
	private int index;
	private double costs;
	
	public AddConstantChange(Resource res, EObject toObj, EStructuralFeature feat, Object value, int index) {
		super(toObj,feat,res);
		this.value = value;
		this.index = index;
	}

	@Override
	public AddConstantChange clone() {
		return new AddConstantChange(forResource(),forObject(), forFeature(), value, index);
	}

	
	@Override
	public void transfer(EcoreTransferFunction func) {
		value = func.transfer(value);
		super.transfer(func);
	}
	
	@Override
	public void checkChange() {
		super.checkChange();
		if (value instanceof EObject && ((EObject) value).eResource() == null) {
			throw new RuntimeException();
		}
	}
	
	@Override
	public Undoer execute() {
		costs = 0;
		costs+= MyResource.get(forResource()).getCostProvider().getFunction(value).getCosts(null, value);
		MyEcoreUtil.addValue(forObject(), forFeature(), index, value);
		
		return ()->{
			MyEcoreUtil.removeValueAtIndex(forObject(), forFeature(), index);
		};
	}


	@Override
	protected String getSimpleName() {
		return "Add to ";
	}

	@Override
	protected String getAdditionalValueName() {
		return ""+value;
	}
	
	public int hashCode() {
		return super.hashCode()+Objects.hashCode(value)+index*577;
	}
	
	public boolean equals(AddConstantChange o) {
		return o.index == index && Objects.equals(value, o.value); 
	}
	

	@Override
	public void addFixReferencesLocal(Collection<FixAttemptReference> refs) {
		refs.add(new FixAttemptFeatureReferenceImpl(forObject(), forFeature(),
				index, value));
	}
	

	@Override
	public void normalizeMap(EObjectChangeMap map) {
		map.getFeatureChanges(forObject(), forFeature()).add(this);
	}

	@Override
	public double getCosts() {
		return costs;
	}

	@Override
	public boolean isIdentity() {
		return forFeature().isUnique() && MyEcoreUtil.getAsCollection(forObject(), forFeature()).contains(value);
	}
	
}

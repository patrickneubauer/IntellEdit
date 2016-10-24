package at.ac.tuwien.big.autoedit.change.basic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import at.ac.tuwien.big.autoedit.change.BasicChange;
import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.EObjectChangeMap;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptFeatureReferenceImpl;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReference;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class BasicSetConstantChange extends AbstractFeatureChange<BasicSetConstantChange> implements FeatureChange<BasicSetConstantChange> {

	private Object value;
	private int index;
	
	public BasicSetConstantChange(Resource res, EObject toObj, EStructuralFeature feat, Object value) {
		super(toObj,feat,res);
		this.value = value;
		this.index = -1;
	}
	
	public BasicSetConstantChange(Resource res, EObject toObj, EStructuralFeature feat, int index, Object value) {
		this(res,toObj,feat,value);
		this.index = index;
	}

	@Override
	public BasicSetConstantChange clone() {
		return new BasicSetConstantChange(forResource(),forObject(), forFeature(), index, value);
	}

	
	@Override
	public void transfer(EcoreTransferFunction func) {
		super.transfer(func);
		value = func.transfer(value);
	}
	
	@Override
	public Undoer execute() {
		try {
		costs = 0;
		if (forFeature().isMany()) {
		
			if (index >= 0) {
				List l = (List)forObject().eGet(forFeature());
				int oldSize = l.size();
				int realIndex = Math.min(index,l.size());
				if (realIndex == l.size()) {
					l.add(value);
					costs+= costProvider().getFunction(value).getCosts(null,value);
					return ()->{
						((List)forObject().eGet(forFeature())).remove(realIndex);
					};
				} else {
					Object oldValue = l.get(index);
					costs+= costProvider().getFunction(value).getCosts(l.get(index),value);					
					l.set(index,value);
					return ()->{
						l.set(index,oldValue);
					};
				}
			} else {
				Collection cur = new ArrayList();
				Collection then = MyEcoreUtil.asCollection(value);
				for (Object o: cur) {
					if (!then.contains(o)) {
						costs+= costProvider().getFunction(o).getCosts(o, null);
					}
				}
				for (Object o: then) {
					if (!cur.contains(o)) {
						costs+= costProvider().getFunction(o).getCosts(null,o);
					}
				}
				forObject().eSet(forFeature(), value);
				
				return ()->{
					forObject().eSet(forFeature(), cur);
				};
			}
		} else {
			Object cur = forObject().eGet(forFeature());
			costs+= costProvider().getFunction(cur).getCosts(cur, value);
			forObject().eSet(forFeature(), value);
			return ()->{
				Object bla = cur;
				forObject().eSet(forFeature(), bla);
			};
		}
		} catch (Exception e) {
			return ()->{};
		}
	}
	

	@Override
	protected String getSimpleName() {
		return "Set ";
	}

	@Override
	protected String getAdditionalValueName() {
		return " to "+value;
	}

	public int hashCode() {
		return super.hashCode()+Objects.hashCode(value);
	}
	

	@Override
	public boolean equals(BasicSetConstantChange o) {
		return Objects.equals(value, o.value); 
	}
	

	@Override
	public void addFixReferencesLocal(Collection<FixAttemptReference> refs) {
		refs.add(new FixAttemptFeatureReferenceImpl(forObject(), forFeature(),
				-1, null));
	}
	
	
	@Override
	public void normalizeMap(EObjectChangeMap map) {
		List<BasicChange<?>> baseChanges = map.getFeatureChanges(forObject(), forFeature());
		baseChanges.clear();
		baseChanges.add(this);
	}
	
	private double costs;
	
	@Override
	public double getCosts() {
		return  costs;
	}
	

	@Override
	public boolean isIdentity() {
		return MyEcoreUtil.getAsCollection(forObject(), forFeature()).equals(MyEcoreUtil.asCollection(value));
	}
	
}

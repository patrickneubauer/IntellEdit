package at.ac.tuwien.big.autoedit.change.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.EObjectChangeMap;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptFeatureReferenceImpl;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReference;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class BasicClearConstantChange extends AbstractFeatureChange<BasicClearConstantChange> implements FeatureChange<BasicClearConstantChange> {

	public BasicClearConstantChange(Resource res, EObject toObj, EStructuralFeature feat) {
		super(toObj,feat,res);
	}

	@Override
	public BasicClearConstantChange clone() {
		return new BasicClearConstantChange(forResource(),forObject(), forFeature());
	}

	
	@Override
	public void transfer(EcoreTransferFunction func) {
		super.transfer(func);
	}
	
	private double costs = 0.0;
	
	@Override
	public Undoer execute() {
		costs = 0.0;
		if (forObject() == null) {
			return ()->{};
		}
		if (forFeature() instanceof EReference) {
			EReference ref = (EReference)forFeature();
			if (ref.isContainment()) {
				//Delete instead
				List<Undoer> allUndoers = new ArrayList<Undoer>();
				Collection col = new ArrayList<>(MyEcoreUtil.getAsCollection(forObject(), ref));
				for (Object o: col) {
					if (o == null) {continue;}
					DeleteObjectChange doc = new DeleteObjectChange((EObject)o,forResource());
					costs+= doc.getCosts();
					allUndoers.add(doc.execute());
				}
				return ()->{
					for (int i = allUndoers.size()-1; i >= 0; --i) {
						allUndoers.get(i).undo();
					}
				};
			}
			
		}
		Object oldTargetVal = null;
		EReference opposite = null;
		if (forFeature() instanceof EReference) {
			EReference ref = (EReference)forFeature();
			opposite = ref.getEOpposite();
		} 		
		CostProvider prov = costProvider();
		EReference fopposite = opposite;
		if (FeatureMapUtil.isMany(forObject(), forFeature())) {
			List c = (List)forObject().eGet(forFeature());
			List l = new ArrayList(c);
			for (Object o: l) {
				costs+= prov.getFunction(o).getCosts(o, null);
				c.remove(o);
			}
			if (opposite != null) {
				for (Object o: l) {
					if (o == null) {continue;}
					EObject eo = (EObject)o;
					costs+= prov.getFunction(forObject()).getCosts(forObject(), null);
					if (fopposite.isMany()) {
						((Collection)eo.eGet(fopposite)).remove(forObject());						
					} else {
						eo.eUnset(fopposite);
					}
				}
			}
			return ()->{
				((List)forObject().eGet(forFeature())).addAll(l);
				if (fopposite != null) {
					for (Object o: l) {
						if (o == null) {continue;}
						EObject eo = (EObject)o;
						if (fopposite.isMany()) {
							((Collection)eo.eGet(fopposite)).add(forObject());
						} else {
							eo.eSet(fopposite, forObject());
						}
					}
				}
			};
		} else {
			Object cur = forObject().eGet(forFeature());
			if (cur != null) {
				costs+= prov.getFunction(cur).getCosts(cur, null);
				forObject().eUnset(forFeature());
				return ()->{
					forObject().eSet(forFeature(), cur);
				};
			} else {
				return ()->{};
			}
		}
		
	}
	

	@Override
	protected String getSimpleName() {
		return "Clear ";
	}

	@Override
	protected String getAdditionalValueName() {
		return "";
	}
	
	public int hashCode() {
		return super.hashCode();
	}


	@Override
	public boolean equals(BasicClearConstantChange o) {
		return true; 
	}
	

	@Override
	public void addFixReferencesLocal(Collection<FixAttemptReference> refs) {
		refs.add(new FixAttemptFeatureReferenceImpl(forObject(), forFeature(),
				-1, null));
	}
	
	@Override
	public void normalizeMap(EObjectChangeMap map) {
		map.getFeatureChanges(forObject(), forFeature()).clear();
		map.getFeatureChanges(forObject(), forFeature()).add(this);
	}
	

	@Override
	public double getCosts() {
		return costs;
	}
	

	@Override
	public boolean isIdentity() {
		return MyEcoreUtil.getAsCollection(forObject(), forFeature()).isEmpty();
	}
	
}

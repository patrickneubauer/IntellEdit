package at.ac.tuwien.big.autoedit.change.basic;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import at.ac.tuwien.big.autoedit.change.BasicChange;
import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.CostProvider;
import at.ac.tuwien.big.autoedit.change.EObjectChangeMap;
import at.ac.tuwien.big.autoedit.change.Undoer;
import at.ac.tuwien.big.autoedit.ecore.util.MyEcoreUtil;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReference;
import at.ac.tuwien.big.autoedit.oclvisit.FixAttemptReferenceImpl;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.TransferFunction;

public class DeleteObjectChange implements ObjectChange<DeleteObjectChange> {
	
	private EObject toDel;
	private Resource res;
	
	public DeleteObjectChange(EObject toDel, Resource res) {
		this.toDel = toDel;
		this.res = res;
	}
	
	public Resource forResource() {
		return res;
	}

	@Override
	public DeleteObjectChange clone() {
		return new DeleteObjectChange(toDel,res);
	}

	@Override
	public void transfer(EcoreTransferFunction func) {
		toDel = func.forward(toDel);
		res = func.forwardResource();
	}


	@Override
	public EObject forObject() {
		return toDel;
	}

	
	private double costs = 0.0;
	
	@Override
	public Undoer execute() {
		if (toDel == null) {
			return ()->{};
		}
		CostProvider prov = costProvider();
		costs = prov.getDeleteCosts(toDel);
		
		//I have to preserve all edges ...
		final Collection<EStructuralFeature.Setting> settings = MyEcoreUtil.getReferences(toDel);
		final List<Integer> addIndices = new ArrayList<>();
		for (EStructuralFeature.Setting setting: settings) {
		   if (FeatureMapUtil.isMany(setting.getEObject(), setting.getEStructuralFeature()))
		    {
			   List<?> list = (List<?>)setting.get(false);
			   if (list != null) {
				   addIndices.add(list.indexOf(toDel));
		       		list.remove(toDel);
		       		costs+= prov.getFunction(toDel).getCosts(toDel, null);
			   }
		    }
		    else
		    {
		      addIndices.add(-1);
		       setting.unset();
		       costs+= prov.getFunction(toDel).getCosts(toDel, null);
		    }
		}
		InternalEObject ieo = (toDel instanceof InternalEObject)?((InternalEObject)toDel):null;
		final Resource addInResource;
		if (ieo != null) {
			addInResource = ieo.eDirectResource();
		} else {
			addInResource = (toDel.eResource().getContents().contains(toDel))?toDel.eResource():null;
		}
		final EObject container = (ieo==null)?toDel.eContainer():ieo.eInternalContainer();
		final EReference feature = toDel.eContainmentFeature();
		int idx = -1;
	    if (container != null)
	    { 
	      if (FeatureMapUtil.isMany(container, feature))
	      {
	    	idx = ((EList<?>)container.eGet(feature)).indexOf(toDel); 
	        ((EList<?>)container.eGet(feature)).remove(toDel);
	        costs+= prov.getFunction(toDel).getCosts(toDel, null);
	      }
	      else
	      {
	        container.eUnset(feature);
	        costs+= costProvider().getFunction(toDel).getCosts(toDel, null);
	      }
	    }
	    if (addInResource != null) {
	    	addInResource.getContents().remove(toDel);
	    	costs+= costProvider().getFunction(toDel).getCosts(toDel, null);
	    }
	    Stack<Undoer> deletedContained = new Stack<Undoer>();
	    for (EReference ref: toDel.eClass().getEAllReferences()) {
	    	if (ref.isContainment()) {
	    		BasicClearConstantChange bcc = new BasicClearConstantChange(res, toDel, ref);
	    		deletedContained.add(bcc.execute());
	    		costs+= bcc.getCosts();
	    	}
	    }
	    MyResource.get(res).trackDeleted(toDel);
	    final int fidx = idx;
		return ()->{
			if (addInResource != null) {
				addInResource.getContents().add(toDel);
			}
			if (container != null) {
				if (FeatureMapUtil.isMany(container, feature)) {
					if (fidx != -1) {
						try {
						((EList)container.eGet(feature)).add(fidx, toDel);
						} catch (Exception e) {
							e.printStackTrace();
							System.err.println(e.getMessage());
						}
					}
				} else {
					container.eSet(feature, toDel);
				}
			}
			int myInd = 0;
			for (EStructuralFeature.Setting setting: settings) {
				if (FeatureMapUtil.isMany(setting.getEObject(), setting.getEStructuralFeature())) {
					int myIdx = addIndices.get(myInd);
					if (myIdx != -1) {
						((List)setting.get(false)).add(myIdx,toDel);
					}
			    } else {
			    	setting.set(toDel);
			    }
				++myInd;
			}
			while (!deletedContained.isEmpty()) {
				deletedContained.pop().undo();
			}
			MyResource.get(res).trackCreated(toDel);
		};
		
	}
	
	public int hashCode() {
		return 2123*Objects.hashCode(toDel);
	}
	
	@Override
	public boolean equals(Object o) {
		return (o instanceof DeleteObjectChange) && Objects.equals(((DeleteObjectChange)o).toDel,toDel); 
	}


	@Override
	public void addFixReferencesLocal(Collection<FixAttemptReference> refs) {
		refs.add(new FixAttemptReferenceImpl(forObject(),true));
	}
	


	@Override
	public void normalizeMap(EObjectChangeMap map) {
		map.clearChanges(forObject());
		List<BasicChange<?>> baseChanges = map.getPureObjectChanges(forObject());
		baseChanges.add(this);
	}


	@Override
	public double getCosts() {
		return costs;
	}

	@Override
	public void removeNonretained(Set<Change<?>> retain, Set<Change<?>> remove) {}
	

	@Override
	public String toString(EObject context) {
		return "Delete " + MyEcoreUtil.getEObjName(toDel,context);
	}
	
	public String toString() {
		return "Delete " + MyEcoreUtil.getEObjName(toDel);
	}
	
	@Override
	public String getName(EObject context) {
		return "Delete "+ MyEcoreUtil.getEObjName(toDel,context);
	}


	@Override
	public boolean isIdentity() {
		return toDel == null || !toDel.eResource().equals(forResource());
	}
}

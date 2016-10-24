package at.ac.tuwien.big.autoedit.search.local;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.ecore.OCLExpression;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.evaluate.Evaluable;
import at.ac.tuwien.big.autoedit.search.local.impl.SearchTask;
import at.ac.tuwien.big.autoedit.transfer.ETransferrable;

public interface LocalSearchInterface extends ETransferrable<LocalSearchInterface>, SearchTask {
	
	
	public void setNeighborhood(NeighborhoodProvider provider);
	
	public NeighborhoodProvider getNeighborhood();
	
	public EObject getContext();
	
	public SimpleStream<Change<?>> getStream();
	
	public void abortSearch();
	
	public void initSearch();
	
	public boolean doSomeSearch();
	
	public boolean isFinished();

	boolean isObsolete(Resource curResource);

	public void copyFrom(LocalSearchInterface localSearchInterface, boolean reuseResource);

	Evaluable<?,?> getOriginalEvaluable();
	
	

}

package at.ac.tuwien.big.autoedit.xtext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.EvaluationEnvironment;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.proposal.Proposal;
import at.ac.tuwien.big.autoedit.proposal.ProposalList;
import at.ac.tuwien.big.autoedit.proposal.impl.ProposalImpl;
import at.ac.tuwien.big.autoedit.proposal.impl.ProposalListImpl;
import at.ac.tuwien.big.autoedit.search.local.LocalSearchInterface;
import at.ac.tuwien.big.autoedit.search.local.impl.LocalSearchInterfaceImpl;

public  class ExpressionQuickfixInfo<T extends Comparable<T>> {
	private String exprid;
	private Map<String,ProposalList<T,?>> subIdToChangeMap = new WeakHashMap<>();

	public ExpressionQuickfixInfo(String id) {
		this.exprid = id;
	}
	 
	public synchronized ProposalList<T,?> getChanges(String subId) {
		return subIdToChangeMap.getOrDefault(subId,new ProposalListImpl());
	}
	
	public synchronized List<QuickfixReference> getQuickfix(String subId, String contextUri) {
		ProposalList<T,?> changes = getChanges(subId);
		List<QuickfixReference> ret = new ArrayList<QuickfixReference>();
		for (Proposal<T,?> prop: changes) {
			Change<?> change = prop.getChange();
			String str = "";
			if (prop.getQuality() instanceof Number) {
				str = Change.costToInvisible(((Number)prop.getQuality()).doubleValue(), false);
			}
			String invisibleString = Change.costToInvisible(prop.getCurQuality(), false)+str+Change.costToInvisible(prop.getCosts(),true);
			ret.add(new QuickfixReferenceImpl(change.getName(contextUri),
				change.toString(contextUri)+", Local-Score: " + prop.getCurQuality()+", Score: " + prop.getQuality()+", Cost "+prop.getCosts(), change,
				new double[]{-prop.getCurQuality(),-(Double)prop.getQuality(),prop.getCosts()}));
		}
		return ret;
	}
	
	public synchronized void addChange(String id, Proposal<T,?> prop) {
		ProposalList<T,?> cur = subIdToChangeMap.get(id);
		if (cur == null) {
			subIdToChangeMap.put(id, cur = new ProposalListImpl());
		}
		((ProposalList)cur).addProposal(prop);
	}
	
	public String getId() {
		return exprid;
	}	

	public synchronized void clear() {
		subIdToChangeMap.clear();
	}

	
	private LocalSearchInterface[] searches = new LocalSearchInterfaceImpl[0];
	
	public LocalSearchInterface[] getSearches() {
		return searches;
	}
	
	public boolean[] resetOrReeinit(Resource curResource, LocalSearchInterface... newSearches) {
		if (searches.length < newSearches.length) {
			searches = Arrays.copyOf(searches, newSearches.length);
		}
		boolean[] ret = new boolean[searches.length];
		for (int i = 0; i < Math.min(searches.length,newSearches.length); ++i) {
			if (searches[i] == null) {
				searches[i]  = newSearches[i];
				ret[i] = true;
			} else if (searches[i].isObsolete(curResource)) {
				searches[i].abortSearch();
				searches[i]  = newSearches[i];
				newSearches[i].copyFrom(searches[i],false);
				ret[i] = true;
			} else {
				searches[i].abortSearch();
				searches[i]  = newSearches[i];
				newSearches[i].copyFrom(searches[i],true);
				ret[i] = true;
			}
		}
		return ret;
	}
	
	public void finalize() throws Throwable {
		super.finalize();
		for (int i = 0; i < searches.length; ++i) {
			if (searches[i] != null) {
				searches[i].abortSearch();
			}
		}
	}

}

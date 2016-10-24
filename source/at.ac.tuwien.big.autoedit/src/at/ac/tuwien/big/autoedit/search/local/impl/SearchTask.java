package at.ac.tuwien.big.autoedit.search.local.impl;

import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.search.local.LocalSearchInterface;

public interface SearchTask extends Comparable<SearchTask> {
	
	public void run();
	
	public boolean isFinished();
	
	public boolean isObsolete(Resource curResource);
	
	public double getQuality();
	
	public default int compareTo(SearchTask o) {
		return -Double.compare(getQuality(),o.getQuality());
	}
	

}

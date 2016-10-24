package at.ac.tuwien.big.autoedit.search.local;

import at.ac.tuwien.big.autoedit.proposal.Proposal;

public interface Processor<T> {
	
	public Proposal<?, ?> process(T original, T processed, double... evaluationar);

}

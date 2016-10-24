package at.ac.tuwien.big.autoedit.search.local;

import at.ac.tuwien.big.autoedit.proposal.Proposal;

public interface SimpleStream<T> {
	
	public Proposal<?,?> add(T original, T processed, double... evaluationar);
	
	public static<T> SimpleStream<T> getStream(Processor<T> proc) {
		return new SimpleStream<T>() {
			
			@Override
			public synchronized Proposal<?,?> add(T original, T processed, double ... evaluationar) {
				return proc.process(original,processed, evaluationar);				
			}
		};
	}

}

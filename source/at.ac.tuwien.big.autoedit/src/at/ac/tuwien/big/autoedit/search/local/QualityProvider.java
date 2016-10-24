package at.ac.tuwien.big.autoedit.search.local;

import at.ac.tuwien.big.autoedit.proposal.Proposal;

public interface QualityProvider<T extends Comparable<T>> {

	public T getQuality(Proposal<T, ?> prop);
}

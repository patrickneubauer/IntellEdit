package at.ac.tuwien.big.autoedit.search.local;

import at.ac.tuwien.big.autoedit.proposal.Proposal;

public interface CurQualityProvider {
	
	public double getQuality(Proposal<?, ?> prop);

}

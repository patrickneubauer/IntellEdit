package at.ac.tuwien.big.autoedit.proposal.impl;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.proposal.Proposal;

public class ProposalImpl<T extends Comparable<T>> implements Proposal<T,ProposalImpl<T>>, Comparable<ProposalImpl<T>> {
	
	public ProposalImpl(Change<?> ch) {
		this.change = ch;
	}
	
	private Change<?> change;
	private T quality;
	private double curQuality;
	private double costs;

	@Override
	public Change<?> getChange() {
		return change;
	}

	@Override
	public T getQuality() {
		return quality;
	}

	@Override
	public void setQuality(T quality) {
		this.quality = quality;
	}

	@Override
	public double getCurQuality() {
		return curQuality;
	}

	@Override
	public void setCurQuality(double quality) {
		this.curQuality = quality;
	}


	@Override
	public double getCosts() {
		return costs;
	}

	@Override
	public void setCosts(double costs) {
		this.costs = costs;
	}
	
	public String toString() {
		return "Proposal curQ: " + curQuality+", q: "+quality+", costs: "+costs+":\n"+change;
	}


}

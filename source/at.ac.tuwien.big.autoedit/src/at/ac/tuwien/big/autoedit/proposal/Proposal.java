package at.ac.tuwien.big.autoedit.proposal;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators.GetQualityFunc;
import at.ac.tuwien.big.autoedit.proposal.impl.ProposalImpl;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public interface Proposal<T extends Comparable<T>, U extends Proposal<T,U>>  extends Comparable<U> {
	
	public Change<?> getChange();
	
	public T getQuality();
	
	public void setQuality(T quality);
	
	public double getCurQuality();
	
	public void setCurQuality(double quality);
	
	public static<T extends Comparable<T>> ProposalImpl<T> fromChange(Change<?> ch) {
		return new ProposalImpl<T>(ch);
	}
	/*
	public default void revalidateIfNecessary() {
		if (needRevalidate()) {
			revalidate();
	}
	}
	
	public boolean needRevalidate();

	public void revalidate(MyResource newResource, EcoreTransferFunction transfer, );*/	
	
	
	public default int compareTo(U other) {
		int ret = -Double.compare(getCurQuality(), other.getCurQuality());
		if (ret != 0) {
			return ret;
		}
		ret = -getQuality().compareTo(other.getQuality());
		if (ret != 0) {
			return ret;
		}
		ret = Double.compare(getCosts(),other.getCosts());
		return ret;
	}

	public double getCosts();
	
	public void setCosts(double costs);

}

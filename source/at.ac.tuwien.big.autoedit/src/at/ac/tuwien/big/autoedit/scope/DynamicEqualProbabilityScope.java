package at.ac.tuwien.big.autoedit.scope;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import at.ac.tuwien.big.autoedit.scope.helper.GetFunc;

public class DynamicEqualProbabilityScope<T> implements ValueScope<T,Boolean>{
	
	private GetFunc<? extends List<T>> t;
	
	private DynamicEqualProbabilityScope(GetFunc<? extends List<T>> t) {
		this.t = t;
	}
	
	public static<T> DynamicEqualProbabilityScope<T> fromList(GetFunc<? extends List<T>> listf) {
		return new DynamicEqualProbabilityScope<>(listf);
	}
	
	private List<T> t() {
		return t.get();
	}

	@Override
	public boolean contains(T sol) {
		return t().contains(sol);
	}

	@Override
	public Boolean getQuality(T sol) {
		return t().contains(sol);
	}

	@Override
	public Iterator<T> iterator() {
		return t().iterator();
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public Iterator<T> sample() {
		return new EqualProbabilitySampler<T>(t());
	}



}


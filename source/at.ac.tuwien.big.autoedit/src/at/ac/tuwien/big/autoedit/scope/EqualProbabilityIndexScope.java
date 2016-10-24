package at.ac.tuwien.big.autoedit.scope;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

import at.ac.tuwien.big.autoedit.scope.helper.CountingIterator;
import at.ac.tuwien.big.autoedit.scope.helper.ListGenerator;

public class EqualProbabilityIndexScope implements ValueScope<Integer,Boolean>{
	
	public static interface Sizer {
		public int size();
	}
	
	private Sizer sizer;
	
	public EqualProbabilityIndexScope(Sizer sizer) {
		this.sizer = sizer;
	}

	@Override
	public boolean contains(Integer sol) {
		return sol>=0 && sol < sizer.size();
	}

	@Override
	public Boolean getQuality(Integer sol) {
		return contains(sol);
	}

	@Override
	public Iterator<Integer> iterator() {
		return new CountingIterator(sizer.size());
	}

	@Override
	public boolean isFinite() {
		return true;
	}
	
	private Random random = new Random();

	@Override
	public Iterator<Integer> sample() {
		return new Iterator<Integer>() {

			@Override
			public boolean hasNext() {
				return sizer.size()>0;
			}

			@Override
			public Integer next() {
				return random.nextInt(sizer.size());
			}
			
		};
	}
	

	@Override
	public Integer sampled() {
		int max = sizer.size();
		if (max == 0) {
			return null;
		}
		return sample().next();
	}

}

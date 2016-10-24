package at.ac.tuwien.big.autoedit.change.primitive;

import java.util.Iterator;

import at.ac.tuwien.big.autoedit.scope.ValueScope;

public class LogIntChangeScope implements ValueScope<Integer, Boolean> {
	private LogIntScope base = LogIntScope.INSTANCE;
	
	private int curValue;
	
	public LogIntChangeScope(int curValue)  {
		this.curValue = curValue;
	}

	@Override
	public boolean contains(Integer sol) {
		return true;
	}

	@Override
	public Boolean getQuality(Integer sol) {
		return true;
	}

	private Iterator<Integer> addIter(Iterator<Integer> base) {
		return new Iterator<Integer>() {

			@Override
			public boolean hasNext() {
				return base.hasNext();
			}

			@Override
			public Integer next() {
				return curValue+base.next();
			}
			
		};
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return addIter(base.iterator());
	}

	@Override
	public boolean isFinite() {
		return false;
	}

	@Override
	public Iterator<Integer> sample() {
		return addIter(base.sample());
	}

}

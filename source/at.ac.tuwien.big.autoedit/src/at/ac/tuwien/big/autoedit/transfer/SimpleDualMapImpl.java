package at.ac.tuwien.big.autoedit.transfer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class SimpleDualMapImpl<X,Y> implements SimpleDualMap<X, Y> {
	private Map<X,Y> valueMap = new HashMap<>();
	private Map<Y,X> sourceMap = new HashMap<>();

	@Override
	public void put(X x, Y y) {
		sourceMap.put(y, x);
		valueMap.put(x, y);
	}

	@Override
	public Y getValue(X x) {
		return valueMap.get(x);
	}

	@Override
	public X getSource(Y y) {
		return sourceMap.get(y);
	}
	
	@Override
	public Collection<? extends X> getDomain() {
		return valueMap.keySet();
	}


	@Override
	public Collection<? extends Y> getRange() {
		return sourceMap.keySet();
	}

	@Override
	public void clear() {
		valueMap.clear();
		sourceMap.clear();
	}
}

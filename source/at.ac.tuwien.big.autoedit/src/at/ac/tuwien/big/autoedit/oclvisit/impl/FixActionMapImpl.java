package at.ac.tuwien.big.autoedit.oclvisit.impl;

import java.util.HashMap;
import java.util.Map;

import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.oclvisit.FixActionMap;

public class FixActionMapImpl implements FixActionMap {
	private Map<ChangeType<?, ?>, Integer> map = new HashMap<ChangeType<?,?>, Integer>();

	@Override
	public Map<ChangeType<?, ?>, Integer> priorityMap() {
		return map;
	}

}

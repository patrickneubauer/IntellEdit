package at.ac.tuwien.big.autoedit.search.local.impl;

import java.util.Stack;

import at.ac.tuwien.big.autoedit.change.Change;

public class LocalSearchSolution {
	private Stack<Change<?>> changes = new Stack<Change<?>>();
	
	public LocalSearchSolution(Stack<Change<?>> changes) {
		this.changes = changes;
	}
	
	public Stack<Change<?>> getChanges() {
		return changes;
	}
}

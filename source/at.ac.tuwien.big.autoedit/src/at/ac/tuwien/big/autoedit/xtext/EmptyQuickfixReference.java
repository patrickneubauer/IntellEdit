package at.ac.tuwien.big.autoedit.xtext;

import at.ac.tuwien.big.autoedit.change.Change;

public class EmptyQuickfixReference implements QuickfixReference {

	@Override
	public Change<?> getChange() {
		return Change.empty();
	}

	@Override
	public String getDescription() {
		return "";
	}

	@Override
	public String getName() {
		return "";
	}

}

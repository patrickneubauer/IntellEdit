package at.ac.tuwien.big.autoedit.xtext;

import at.ac.tuwien.big.autoedit.change.Change;

public interface QuickfixReference {
	
	public Change<?> getChange();
	
	public String getDescription();

	public String getName();
	
	public default String getImage() {
		return "notfound.png";
	}
}

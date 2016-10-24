package at.ac.tuwien.big.autoedit.xtext;

import java.util.Objects;

import at.ac.tuwien.big.autoedit.change.Change;

public class QuickfixReferenceImpl implements QuickfixReference {
	
	private String name;
	private String description;
	private Change<?> change;
	
	public QuickfixReferenceImpl(String name, String description, Change<?> change) {
		this.name = name;
		this.description = description;
		this.change = change;
	}

	@Override
	public Change<?> getChange() {
		return change;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public int hashCode() {
		return Objects.hash(change);
	}
	
	public boolean equals(Object other) {
		return (other instanceof QuickfixReference) &&
					Objects.equals(change,((QuickfixReference)other).getChange());
	}

}

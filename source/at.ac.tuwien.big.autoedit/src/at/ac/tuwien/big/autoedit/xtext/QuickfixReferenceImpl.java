package at.ac.tuwien.big.autoedit.xtext;

import java.util.Objects;

import at.ac.tuwien.big.autoedit.change.Change;

public class QuickfixReferenceImpl implements QuickfixReference {
	
	private String name;
	private String description;
	private Change<?> change;
	private double[] compareValues;
	
	public QuickfixReferenceImpl(String name, String description, Change<?> change, double[] compareValues) {
		this.name = name;
		this.description = description;
		this.change = change;
		this.compareValues = compareValues;
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

	@Override
	public int compareTo(QuickfixReference o) {
		if (!(o instanceof QuickfixReferenceImpl)) {
			return 0;
		}
		QuickfixReferenceImpl qri = (QuickfixReferenceImpl)o;
		for (int i = 0; i < compareValues.length; ++i) {
			int ret = Double.compare(compareValues[i], qri.compareValues[i]);
			if (ret != 0) {
				return ret;
			}
		}
		return 0;
	}

}

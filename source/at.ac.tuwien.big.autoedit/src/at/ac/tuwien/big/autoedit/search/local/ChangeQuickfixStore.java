package at.ac.tuwien.big.autoedit.search.local;

import java.util.Set;
import java.util.SortedSet;

import at.ac.tuwien.big.autoedit.change.Change;

public interface ChangeQuickfixStore<U extends Comparable<U>> {
	
	public default SortedSet<ChangeQuickfix<?, U>> getQuickfixes(U minQuality) {
		return getAllQuickfixes().tailSet(new ChangeQuickfix() {
			public void setQuality(Comparable quality) {}
			public Change<?> getChange() {return null;}

			@Override
			public U getQuality() {
				return minQuality;
			}});
	}
	
	public SortedSet<ChangeQuickfix<?, U>> getAllQuickfixes();

}

package at.ac.tuwien.big.autoedit.scope;

public class ComparablePairImpl<T,U extends Comparable<U>> implements ComparablePair<T, U> {
	private T solution;
	private U quality;
	
	public ComparablePairImpl(T solution, U quality) {
		this.solution = solution;
		this.quality = quality;
	}


	@Override
	public T getSolution() {
		return solution;
	}

	@Override
	public U getQuality() {
		return quality;
	}

}

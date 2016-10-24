package at.ac.tuwien.big.autoedit.scope;

public interface QualityProvider<Solution,Quality> {

	public Quality provide(Solution sol);
}

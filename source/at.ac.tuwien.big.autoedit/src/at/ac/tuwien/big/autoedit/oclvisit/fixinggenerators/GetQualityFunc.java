package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;

public interface GetQualityFunc {
	
	public double getQuality(int index, FixAttempt attempt);

}

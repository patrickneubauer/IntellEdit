package at.ac.tuwien.big.autoedit.fixer;

import at.ac.tuwien.big.simpleregexp.RegExpAlgorithm;
import dk.brics.automaton.Automaton;

public interface MakeConformRegexp extends FixAttempt {


	public Automaton getAutomaton();
}

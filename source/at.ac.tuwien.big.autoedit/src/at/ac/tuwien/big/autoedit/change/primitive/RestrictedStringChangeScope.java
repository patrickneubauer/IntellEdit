package at.ac.tuwien.big.autoedit.change.primitive;

import java.util.Iterator;

import at.ac.tuwien.big.autoedit.scope.ValueScope;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;
import at.ac.tuwien.big.simpleregexp.RegExpAlgorithm;
import at.ac.tuwien.big.xtext.util.IteratorUtils;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;

public class RestrictedStringChangeScope implements ValueScope<String, Boolean> {
	
	RegExpAlgorithm algo;
	Automaton automaton;
	
	private RestrictedStringChangeScope(String base, String regexp) {
		automaton = new RegExp(regexp).toAutomaton(); 
		algo = new RegExpAlgorithm(base, regexp);
	}

	@Override
	public boolean contains(String sol) {
		return automaton.run(sol);
	}
	

	@Override
	public Boolean getQuality(String sol) {
		return contains(sol);
	}
			

	@Override
	public Iterator<String> iterator() {
		return algo.getAllStringsIter();
	}

	@Override
	public boolean isFinite() {
		return true;
	}

	@Override
	public Iterator<String> sample() {
		return algo.getSampledIterator();
	}

	@Override
	public void transfer(EcoreTransferFunction transferFunc) {
	}


}

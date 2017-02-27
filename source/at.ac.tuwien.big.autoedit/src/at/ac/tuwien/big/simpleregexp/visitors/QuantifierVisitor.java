package at.ac.tuwien.big.simpleregexp.visitors;

import org.antlr.runtime.tree.CommonTree;

import at.ac.tuwien.big.simpleregexp.ParseStateManager;
import at.ac.tuwien.big.simpleregexp.ParseStateVisitor;

public class QuantifierVisitor extends ParseStateVisitor {

	@Override
	public void visit(CommonTree tree, ParseStateManager manager) {
		throw new UnsupportedOperationException();
	}

}

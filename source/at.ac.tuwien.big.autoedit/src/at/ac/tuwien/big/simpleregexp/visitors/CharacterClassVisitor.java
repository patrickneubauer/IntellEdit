package at.ac.tuwien.big.simpleregexp.visitors;

import java.util.HashSet;
import java.util.Set;

import org.antlr.runtime.tree.CommonTree;

import at.ac.tuwien.big.simpleregexp.Edge;
import at.ac.tuwien.big.simpleregexp.Node;
import at.ac.tuwien.big.simpleregexp.ParseStateManager;
import at.ac.tuwien.big.simpleregexp.ParseStateVisitor;

public class CharacterClassVisitor extends ParseStateVisitor {

	@Override
	public void visit(CommonTree tree, ParseStateManager manager) {
		CharacterClassWrapper wrapper = new CharacterClassWrapper(tree);
		Node current = manager.getCurrentNode();
		Node endNode = manager.newNode(false);
		Edge edge = new Edge(endNode);
		edge.addAcceptable(wrapper.characters);
		current.addEdge(edge);
		manager.setCurrentNode(endNode);
	}

}

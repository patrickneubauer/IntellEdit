package at.ac.tuwien.big.autoedit.change.basic;

import at.ac.tuwien.big.autoedit.change.Parameter;
import at.ac.tuwien.big.autoedit.change.parameter.SimpleParameter;

public class BasicAddConstantChangeType extends ParameterHolderChangeType<BasicAddConstantChangeType, AddConstantChange>
	implements ObjectChangeType<BasicAddConstantChangeType, AddConstantChange> {

	/** ...*/
	
	public BasicAddConstantChangeType() {
		/**Wenn feature gesetzt, dann alle Objekte, die die entsprechende Klasse haben*/
		super(SimpleParameter.fromType(pt, "value", 2)
		);
	}

}

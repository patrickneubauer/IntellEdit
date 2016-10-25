package at.ac.tuwien.big.autoedit.scope.helper;

import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public interface EvalFunc<X,Y>{ 
	public Y eval(X t);

}

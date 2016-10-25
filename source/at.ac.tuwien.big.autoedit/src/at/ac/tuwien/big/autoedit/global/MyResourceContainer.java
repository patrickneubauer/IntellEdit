package at.ac.tuwien.big.autoedit.global;

import java.io.Serializable;
import java.util.Stack;

import org.eclipse.emf.ecore.util.EcoreUtil;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class MyResourceContainer implements Serializable {
	private static final long serialVersionUID = 5731622613264840459L;

	private Stack<EcoreTransferFunction> availableFunctiosns = new Stack<>();
	
	private MyResource myRes;
	
	
	
	public MyResourceContainer(MyResource myRes) {
		this.myRes = myRes;
	}
	

	public synchronized EcoreTransferFunction pullResource() {
		if (availableFunctiosns.isEmpty()) {
			EcoreUtil.Copier copier = new EcoreUtil.Copier();
			MyResource target = myRes.clone(copier); 
			EcoreTransferFunction func = new EcoreMapTransferFunction(myRes.getResource(),
					target.getResource(), copier);
			//MyResource.get(func.forwardResource()).checkResource();
			return func;
		}
		EcoreTransferFunction ret = availableFunctiosns.pop();
		//MyResource.get(ret.forwardResource()).checkResource();
		return ret;
	}
	
	public synchronized void pushResource(EcoreTransferFunction func) {
		//MyResource.get(func.forwardResource()).checkResource();
		availableFunctiosns.push(func);					
	}
}

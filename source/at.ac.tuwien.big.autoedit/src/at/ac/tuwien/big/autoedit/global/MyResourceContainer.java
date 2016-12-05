package at.ac.tuwien.big.autoedit.global;

import java.io.Serializable;
import java.util.Stack;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.transfer.EcoreMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreStrongMapTransferFunction;
import at.ac.tuwien.big.autoedit.transfer.EcoreTransferFunction;

public class MyResourceContainer implements Serializable {
	private static final long serialVersionUID = 5731622613264840459L;

	private Stack<EcoreTransferFunction> availableFunctiosns = new Stack<>();
	private Stack<Resource> resources = new Stack<Resource>();
	
	private MyResource myRes;
	private Resource clonedRes;
	
	
	
	public MyResourceContainer(Resource myRes) {
		this.clonedRes = MyResource.get(myRes).clone();
		this.myRes = MyResource.get(clonedRes);
	}
	

	public synchronized EcoreTransferFunction pullResource() {
		if (availableFunctiosns.isEmpty()) {
			EcoreUtil.Copier copier = new EcoreUtil.Copier();
			Resource target = myRes.clone(copier); 
			resources.add(target);
			EcoreTransferFunction func = new EcoreStrongMapTransferFunction(myRes.getResource(),
					target, copier);
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

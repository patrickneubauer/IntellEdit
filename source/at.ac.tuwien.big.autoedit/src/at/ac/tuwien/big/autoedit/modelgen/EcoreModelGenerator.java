package at.ac.tuwien.big.autoedit.modelgen;

import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.ecore.util.MyResource;

public abstract class EcoreModelGenerator implements ModelGenerator {
	
	private Resource ecoreResource;
	
	
	public EcoreModelGenerator(Resource ecoreResource) {
		this.ecoreResource = ecoreResource;
	}
	
	public Resource getEcoreResource() {
		return ecoreResource;
	}
	
	public abstract EcoreModelgeneratorState generateState(MyResource forRes);

	@Override
	public void populate(Resource res) {
		MyResource myres = MyResource.get(res);
		myres.knowEcore(ecoreResource);
		EcoreModelgeneratorState state = generateState(myres);
		state.augment();
	}
	
	
	

}

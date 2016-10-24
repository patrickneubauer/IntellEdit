package at.ac.tuwien.big.autoedit.oclvisit;

import org.eclipse.emf.ecore.EStructuralFeature;

public interface FixAttemptFeatureReference extends FixAttemptReference {
	
	public EStructuralFeature getFeature();
	
	public int getValueIndex();
	
	public Object getValue();

}

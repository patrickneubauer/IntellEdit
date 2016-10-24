package at.ac.tuwien.big.autoedit.change.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.BasicChange;
import at.ac.tuwien.big.autoedit.change.EObjectChangeMap;

public interface FeatureChange<FC extends FeatureChange<FC>> extends BasicChange<FC> {

	
	@Override
	public Resource forResource();
	
	public EObject forObject();
	
	public EStructuralFeature forFeature();


	
}

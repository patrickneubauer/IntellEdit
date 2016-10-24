package at.ac.tuwien.big.autoedit.oclvisit;

import java.util.Set;

import org.eclipse.emf.ecore.EObject;

import at.ac.tuwien.big.autoedit.fixer.FixAttempt;

public interface FixAttemptReference {

	public EObject forObject();

	public boolean isCompleteObject();
	
	
}

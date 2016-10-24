package at.ac.tuwien.big.autoedit.xtext;

import java.util.Random;

import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.ChangeType;

public interface DynamicValidatorIFace {

	Change<?> randomQuickfix(Random random);

	Resource getResource();

	ChangeType<?, ?> randomChange(Random random);

}

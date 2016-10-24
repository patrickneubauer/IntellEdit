package at.ac.tuwien.big.oclgen;

import org.eclipse.xtext.ISetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class OCL2QuickfixGeneratorSetup implements ISetup {

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
		return Guice.createInjector(new OCL2QuickfixGeneratorModule());
	}

}

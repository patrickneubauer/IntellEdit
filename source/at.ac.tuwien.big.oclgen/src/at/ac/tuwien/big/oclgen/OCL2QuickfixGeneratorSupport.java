package at.ac.tuwien.big.oclgen;

import org.eclipse.emf.mwe2.runtime.workflow.IWorkflowContext;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;
import com.google.inject.Module;

public class OCL2QuickfixGeneratorSupport extends
		AbstractGenericResourceSupport {

	@Override
	protected Module createGuiceModule() {
		return new OCL2QuickfixGeneratorModule();
	}
}

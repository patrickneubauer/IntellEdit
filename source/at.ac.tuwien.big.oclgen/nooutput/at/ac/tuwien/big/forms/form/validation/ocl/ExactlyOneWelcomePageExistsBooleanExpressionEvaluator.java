package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Form;

public class ExactlyOneWelcomePageExistsBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> INSTANCE = new ExactlyOneWelcomePageExistsBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Form self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final java.util.Set<at.ac.tuwien.big.forms.Form> var0 =  (java.util.Set) extents.get(ePackage.getEClassifier("Form"));
final java.util.Collection<at.ac.tuwien.big.forms.Form> var1 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.select(var0, var2 -> {
final java.lang.Boolean var3 = var2.isWelcomeForm();
final java.lang.Boolean var4 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var3, true);
return var4;
});
final java.lang.Integer var5 = var1.size();
final java.lang.Boolean var6 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var5, 1);
return var6;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Form self) {
		return self.eClass().getEStructuralFeature("welcomeForm");
	}
	
}

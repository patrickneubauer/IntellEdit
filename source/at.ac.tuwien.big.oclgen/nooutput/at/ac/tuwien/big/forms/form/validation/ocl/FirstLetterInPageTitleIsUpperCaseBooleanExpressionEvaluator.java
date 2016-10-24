package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Page;

public class FirstLetterInPageTitleIsUpperCaseBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Page> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Page> INSTANCE = new FirstLetterInPageTitleIsUpperCaseBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Page self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final java.lang.String var0 = self.getTitle();
final java.lang.String var1 = var0.substring(1 - 1, 1);
final boolean var2 = var1.matches("[A-Z]");
return var2;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Page self) {
		return self.eClass().getEStructuralFeature("title");
	}
	
}

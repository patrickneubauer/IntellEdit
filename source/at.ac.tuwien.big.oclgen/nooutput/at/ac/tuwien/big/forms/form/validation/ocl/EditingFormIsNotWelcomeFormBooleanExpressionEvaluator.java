package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.RelationshipPageElement;

public class EditingFormIsNotWelcomeFormBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<RelationshipPageElement> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<RelationshipPageElement> INSTANCE = new EditingFormIsNotWelcomeFormBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(RelationshipPageElement self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Form var0 = self.getEditingForm();
final java.lang.Boolean var1 = var0.isWelcomeForm();
final java.lang.Boolean var2 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var1, false);
return var2;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(RelationshipPageElement self) {
		return self.eClass().getEStructuralFeature("editingForm");
	}
	
}

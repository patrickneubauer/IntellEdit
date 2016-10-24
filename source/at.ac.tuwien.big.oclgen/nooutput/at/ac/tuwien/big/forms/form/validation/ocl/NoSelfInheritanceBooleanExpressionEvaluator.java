package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Entity;

public class NoSelfInheritanceBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> INSTANCE = new NoSelfInheritanceBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Entity self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Entity var0 = self.getSuperType();
final java.lang.Boolean var1 = !at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var0, self);
return var1;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Entity self) {
		return self.eClass().getEStructuralFeature("superType");
	}
	
}

package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.CompositeCondition;

public class ComposedConditionTypesAreEqualBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<CompositeCondition> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<CompositeCondition> INSTANCE = new ComposedConditionTypesAreEqualBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(CompositeCondition self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var0 = self.getComposedConditions();
final java.lang.Boolean var1 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.forAll(var0, var2 -> {
final at.ac.tuwien.big.forms.ConditionType var3 = var2.getType();
final at.ac.tuwien.big.forms.ConditionType var4 = self.getType();
final java.lang.Boolean var5 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var3, var4);
return var5;
});
return var1;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(CompositeCondition self) {
		return self.eClass().getEStructuralFeature("composedConditions");
	}
	
}

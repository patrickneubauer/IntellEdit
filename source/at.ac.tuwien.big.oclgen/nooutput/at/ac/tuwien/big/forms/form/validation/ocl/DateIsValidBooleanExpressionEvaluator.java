package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.DateSelectionField;

public class DateIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<DateSelectionField> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<DateSelectionField> INSTANCE = new DateIsValidBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(DateSelectionField self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Attribute var0 = self.getAttribute();
final at.ac.tuwien.big.forms.AttributeType var1 = var0.getType();
final java.lang.Boolean var2 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var1, at.ac.tuwien.big.forms.AttributeType.get(4));
return var2;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(DateSelectionField self) {
		return null;
	}
	
}

package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.SelectionField;

public class SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<SelectionField> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<SelectionField> INSTANCE = new SelectionFieldAttributeTypeIsValidBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(SelectionField self) {
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
final java.lang.Boolean var2 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var1, at.ac.tuwien.big.forms.AttributeType.get(7));
final at.ac.tuwien.big.forms.Attribute var3 = self.getAttribute();
final at.ac.tuwien.big.forms.Enumeration var4 = var3.getEnumeration();
final java.lang.Boolean var5 = !at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var4, null);
final boolean var6 = var2 || var5;
return var6;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(SelectionField self) {
		return null;
	}
	
}

package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Attribute;

public class AttributeTypeIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Attribute> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Attribute> INSTANCE = new AttributeTypeIsValidBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Attribute self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.AttributeType var0 = self.getType();
final java.lang.Boolean var1 = !at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var0, at.ac.tuwien.big.forms.AttributeType.get(0));
final at.ac.tuwien.big.forms.Enumeration var2 = self.getEnumeration();
final java.lang.Boolean var3 = !at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var2, null);
final boolean var4 = var1 ^ var3;
return var4;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Attribute self) {
		return null;
	}
	
}

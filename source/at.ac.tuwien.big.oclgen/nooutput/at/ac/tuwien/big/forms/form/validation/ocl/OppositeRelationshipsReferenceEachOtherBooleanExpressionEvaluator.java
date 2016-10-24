package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Relationship;

public class OppositeRelationshipsReferenceEachOtherBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> INSTANCE = new OppositeRelationshipsReferenceEachOtherBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Relationship self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Relationship var0 = self.getOpposite();
final java.util.Collection<at.ac.tuwien.big.forms.Relationship> var1 = java.util.Arrays.asList(var0);
final java.lang.Boolean var2 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.forAll(var1, var3 -> {
final at.ac.tuwien.big.forms.Relationship var4 = var3.getOpposite();
final java.lang.Boolean var5 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var4, self);
return var5;
});
return var2;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Relationship self) {
		return self.eClass().getEStructuralFeature("opposite");
	}
	
}

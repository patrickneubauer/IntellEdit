package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Relationship;

public class RelationshipMultiplicityIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> INSTANCE = new RelationshipMultiplicityIsValidBooleanExpressionEvaluator();
	
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
final java.lang.Integer var0 = self.getLowerBound();
final boolean var1 = var0 >= 0;
final java.lang.Integer var2 = self.getUpperBound();
final java.lang.Integer var3 = self.getLowerBound();
final boolean var4 = var2 >= var3;
final java.lang.Integer var5 = self.getUpperBound();
final java.lang.Boolean var6 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var5, -1);
final boolean var7 = var4 || var6;
final boolean var8 = var1 && var7;
return var8;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Relationship self) {
		return null;
	}
	
}

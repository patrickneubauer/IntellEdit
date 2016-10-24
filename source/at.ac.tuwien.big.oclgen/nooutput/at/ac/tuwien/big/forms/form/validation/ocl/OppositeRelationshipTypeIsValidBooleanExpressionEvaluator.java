package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Relationship;

public class OppositeRelationshipTypeIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Relationship> INSTANCE = new OppositeRelationshipTypeIsValidBooleanExpressionEvaluator();
	
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
final java.lang.Boolean var1 = !at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var0, null);
final at.ac.tuwien.big.forms.Entity var2 = self.getTarget();
final java.util.Set<at.ac.tuwien.big.forms.Entity> var3 =  (java.util.Set) extents.get(ePackage.getEClassifier("Entity"));
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var4 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.select(var3, var5 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var6 = var5.getFeatures();
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var7 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.select(var6, var8 -> {
final boolean var9 = evalEnv.isKindOf(var8, ePackage.getEClassifier("Relationship"));
return var9;
});
final at.ac.tuwien.big.forms.Relationship var10 = self.getOpposite();
final boolean var11 = org.eclipse.ocl.util.CollectionUtil.includes(var7, var10);
return var11;
});
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var12 = new java.util.LinkedHashSet(var4);
final at.ac.tuwien.big.forms.Entity var13 = var12.iterator().next();
final java.lang.Boolean var14 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var2, var13);
final boolean var15 = !var1 || var14;
return var15;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Relationship self) {
		return null;
	}
	
}

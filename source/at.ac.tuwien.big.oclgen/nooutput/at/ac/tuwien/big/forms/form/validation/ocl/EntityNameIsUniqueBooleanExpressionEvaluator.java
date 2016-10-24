package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Entity;

public class EntityNameIsUniqueBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Entity> INSTANCE = new EntityNameIsUniqueBooleanExpressionEvaluator();
	
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
final java.util.Set<at.ac.tuwien.big.forms.Entity> var0 =  (java.util.Set) extents.get(ePackage.getEClassifier("Entity"));
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var1 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.select(var0, var2 -> {
final java.lang.String var3 = var2.getName();
final java.lang.String var4 = self.getName();
final java.lang.Boolean var5 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var3, var4);
return var5;
});
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var6 = org.eclipse.ocl.util.CollectionUtil.excluding(var1, self);
final boolean var7 = var6.isEmpty();
return var7;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Entity self) {
		return null;
	}
	
}

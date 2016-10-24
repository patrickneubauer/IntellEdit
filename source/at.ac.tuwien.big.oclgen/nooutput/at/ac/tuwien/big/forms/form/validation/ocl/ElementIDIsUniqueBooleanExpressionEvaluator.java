package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.PageElement;

public class ElementIDIsUniqueBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<PageElement> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<PageElement> INSTANCE = new ElementIDIsUniqueBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(PageElement self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final java.util.Set<at.ac.tuwien.big.forms.PageElement> var0 =  (java.util.Set) extents.get(ePackage.getEClassifier("PageElement"));
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var1 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.select(var0, var2 -> {
final java.lang.String var3 = var2.getElementID();
final java.lang.String var4 = self.getElementID();
final java.lang.Boolean var5 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var3, var4);
return var5;
});
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var6 = org.eclipse.ocl.util.CollectionUtil.excluding(var1, self);
final boolean var7 = var6.isEmpty();
return var7;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(PageElement self) {
		return self.eClass().getEStructuralFeature("elementID");
	}
	
}

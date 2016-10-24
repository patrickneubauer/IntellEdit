package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Table;

public class TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Table> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Table> INSTANCE = new TableColumnRefersToTargetEntityFeatureBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Table self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Form var0 = self.getEditingForm();
final at.ac.tuwien.big.forms.Entity var1 = var0.getEntity();
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var2 = var1.getFeatures();
final at.ac.tuwien.big.forms.Form var3 = self.getEditingForm();
final at.ac.tuwien.big.forms.Entity var4 = var3.getEntity();
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var5 = java.util.Arrays.asList(var4);
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var6 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.closure(var5, var7 -> {
final at.ac.tuwien.big.forms.Entity var8 = var7.getSuperType();
return var8;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var9 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var6, var10 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var11 = var10.getFeatures();
return var11;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var12 = org.eclipse.ocl.util.CollectionUtil.union(var2, var9);
final java.util.Collection<at.ac.tuwien.big.forms.Column> var13 = self.getColumns();
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var14 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var13, var15 -> {
final at.ac.tuwien.big.forms.Attribute var16 = var15.getAttribute();
return var16;
});
final boolean var17 = org.eclipse.ocl.util.CollectionUtil.includesAll(var12, var14);
return var17;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Table self) {
		return self.eClass().getEStructuralFeature("columns");
	}
	
}

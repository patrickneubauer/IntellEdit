package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Form;

public class PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> INSTANCE = new PageElementsReferToFormEntityFeaturesBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(Form self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Entity var0 = self.getEntity();
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var1 = var0.getFeatures();
final at.ac.tuwien.big.forms.Entity var2 = self.getEntity();
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var3 = java.util.Arrays.asList(var2);
final java.util.Collection<at.ac.tuwien.big.forms.Entity> var4 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.closure(var3, var5 -> {
final at.ac.tuwien.big.forms.Entity var6 = var5.getSuperType();
return var6;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var7 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var4, var8 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var9 = var8.getFeatures();
return var9;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var10 = org.eclipse.ocl.util.CollectionUtil.union(var1, var7);
final java.util.Collection<at.ac.tuwien.big.forms.Page> var11 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var12 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var11, var13 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var14 = var13.getPageElements();
return var14;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributePageElement> var15 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var12, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributePageElement"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var16 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var15, var17 -> {
final at.ac.tuwien.big.forms.Attribute var18 = var17.getAttribute();
return var18;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var19 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var16, var20 -> {
final at.ac.tuwien.big.forms.Feature var21 = (at.ac.tuwien.big.forms.Feature) var20;
return var21;
});
final java.util.Collection<at.ac.tuwien.big.forms.Page> var22 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var23 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var22, var24 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var25 = var24.getPageElements();
return var25;
});
final java.util.Collection<at.ac.tuwien.big.forms.RelationshipPageElement> var26 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var23, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("RelationshipPageElement"));});
final java.util.Collection<at.ac.tuwien.big.forms.Relationship> var27 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var26, var28 -> {
final at.ac.tuwien.big.forms.Relationship var29 = var28.getRelationship();
return var29;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var30 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var27, var31 -> {
final at.ac.tuwien.big.forms.Feature var32 = (at.ac.tuwien.big.forms.Feature) var31;
return var32;
});
final java.util.Collection<at.ac.tuwien.big.forms.Feature> var33 = org.eclipse.ocl.util.CollectionUtil.union(var19, var30);
final boolean var34 = org.eclipse.ocl.util.CollectionUtil.includesAll(var10, var33);
return var34;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Form self) {
		return null;
	}
	
}

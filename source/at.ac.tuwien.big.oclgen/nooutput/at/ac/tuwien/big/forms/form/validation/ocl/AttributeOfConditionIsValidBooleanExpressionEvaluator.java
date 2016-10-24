package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.Form;

public class AttributeOfConditionIsValidBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<Form> INSTANCE = new AttributeOfConditionIsValidBooleanExpressionEvaluator();
	
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
final java.util.Collection<at.ac.tuwien.big.forms.Page> var0 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var1 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var0, var2 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var3 = var2.getPageElements();
return var3;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributePageElement> var4 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var1, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributePageElement"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var5 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var4, var6 -> {
final at.ac.tuwien.big.forms.Attribute var7 = var6.getAttribute();
return var7;
});
final java.util.Collection<at.ac.tuwien.big.forms.Page> var8 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var9 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var8, var10 -> {
final at.ac.tuwien.big.forms.Condition var11 = var10.getCondition();
return var11;
});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var12 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var9, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var13 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.closureCollection(var12, var14 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var15 = var14.getComposedConditions();
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var16 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var15, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
return var16;
});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var17 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var13, var18 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var19 = var18.getComposedConditions();
return var19;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var20 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var17, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var21 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var20, var22 -> {
final at.ac.tuwien.big.forms.Attribute var23 = var22.getAttribute();
return var23;
});
final java.util.Collection<at.ac.tuwien.big.forms.Page> var24 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var25 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var24, var26 -> {
final at.ac.tuwien.big.forms.Condition var27 = var26.getCondition();
return var27;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var28 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var25, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var29 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var28, var30 -> {
final at.ac.tuwien.big.forms.Attribute var31 = var30.getAttribute();
return var31;
});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var32 = org.eclipse.ocl.util.CollectionUtil.union(var21, var29);
final java.util.Collection<at.ac.tuwien.big.forms.Page> var33 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var34 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var33, var35 -> {
final at.ac.tuwien.big.forms.Condition var36 = var35.getCondition();
return var36;
});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var37 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var34, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var38 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var37, var39 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var40 = var39.getComposedConditions();
return var40;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var41 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var38, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var42 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var41, var43 -> {
final at.ac.tuwien.big.forms.Attribute var44 = var43.getAttribute();
return var44;
});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var45 = org.eclipse.ocl.util.CollectionUtil.union(var32, var42);
final java.util.Collection<at.ac.tuwien.big.forms.Page> var46 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var47 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var46, var48 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var49 = var48.getPageElements();
return var49;
});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var50 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var47, var51 -> {
final at.ac.tuwien.big.forms.Condition var52 = var51.getCondition();
return var52;
});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var53 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var50, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var54 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.closureCollection(var53, var55 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var56 = var55.getComposedConditions();
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var57 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var56, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
return var57;
});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var58 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var54, var59 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var60 = var59.getComposedConditions();
return var60;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var61 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var58, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var62 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var61, var63 -> {
final at.ac.tuwien.big.forms.Attribute var64 = var63.getAttribute();
return var64;
});
final java.util.Collection<at.ac.tuwien.big.forms.Page> var65 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var66 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var65, var67 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var68 = var67.getPageElements();
return var68;
});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var69 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var66, var70 -> {
final at.ac.tuwien.big.forms.Condition var71 = var70.getCondition();
return var71;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var72 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var69, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var73 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var72, var74 -> {
final at.ac.tuwien.big.forms.Attribute var75 = var74.getAttribute();
return var75;
});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var76 = org.eclipse.ocl.util.CollectionUtil.union(var62, var73);
final java.util.Collection<at.ac.tuwien.big.forms.Page> var77 = self.getPages();
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var78 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var77, var79 -> {
final java.util.Collection<at.ac.tuwien.big.forms.PageElement> var80 = var79.getPageElements();
return var80;
});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var81 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var78, var82 -> {
final at.ac.tuwien.big.forms.Condition var83 = var82.getCondition();
return var83;
});
final java.util.Collection<at.ac.tuwien.big.forms.CompositeCondition> var84 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var81, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("CompositeCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var85 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collectCollection(var84, var86 -> {
final java.util.Collection<at.ac.tuwien.big.forms.Condition> var87 = var86.getComposedConditions();
return var87;
});
final java.util.Collection<at.ac.tuwien.big.forms.AttributeValueCondition> var88 = (java.util.Collection) at.ac.tuwien.big.oclgen.OCL2JavaSupport.selectByKind(var85, o -> {
return evalEnv.isKindOf(o, ePackage.getEClassifier("AttributeValueCondition"));});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var89 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.collect(var88, var90 -> {
final at.ac.tuwien.big.forms.Attribute var91 = var90.getAttribute();
return var91;
});
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var92 = org.eclipse.ocl.util.CollectionUtil.union(var76, var89);
final java.util.Collection<at.ac.tuwien.big.forms.Attribute> var93 = org.eclipse.ocl.util.CollectionUtil.union(var45, var92);
final boolean var94 = org.eclipse.ocl.util.CollectionUtil.includesAll(var5, var93);
return var94;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(Form self) {
		return self.eClass().getEStructuralFeature("pages");
	}
	
}

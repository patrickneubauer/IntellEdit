package at.ac.tuwien.big.forms.form.validation.ocl;

import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import at.ac.tuwien.big.forms.RelationshipPageElement;

public class EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator implements at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<RelationshipPageElement> {
	
	public static final at.ac.tuwien.big.oclgen.OCLBooleanExpressionEvaluator<RelationshipPageElement> INSTANCE = new EditingFormRefersToRelationshipTargetBooleanExpressionEvaluator();
	
	@Override
	public boolean isValid(RelationshipPageElement self) {
		final EPackage ePackage = self.eClass().getEPackage();
		final org.eclipse.ocl.EvaluationEnvironment evalEnv = org.eclipse.ocl.ecore.EcoreEnvironmentFactory.INSTANCE.createEvaluationEnvironment();
		final Map<EClass, Set<EObject>> extents = new org.eclipse.ocl.LazyExtentMap<EClass, EObject>((EObject) self) {
		
			// implements the inherited specification
			@Override
			protected boolean isInstance(EClass cls, EObject element) {
				return cls.isInstance(element);
			}
		};
final at.ac.tuwien.big.forms.Relationship var0 = self.getRelationship();
final at.ac.tuwien.big.forms.Entity var1 = var0.getTarget();
final at.ac.tuwien.big.forms.Form var2 = self.getEditingForm();
final at.ac.tuwien.big.forms.Entity var3 = var2.getEntity();
final java.lang.Boolean var4 = at.ac.tuwien.big.oclgen.OCL2JavaSupport.equals(var1, var3);
return var4;
	}
	
	@Override
	public EStructuralFeature findErrorFeature(RelationshipPageElement self) {
		return null;
	}
	
}

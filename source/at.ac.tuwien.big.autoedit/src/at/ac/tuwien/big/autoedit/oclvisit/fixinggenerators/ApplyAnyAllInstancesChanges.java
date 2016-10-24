package at.ac.tuwien.big.autoedit.oclvisit.fixinggenerators;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.ocl.expressions.OperationCallExp;
import org.eclipse.ocl.expressions.PropertyCallExp;

import at.ac.tuwien.big.autoedit.change.Change;
import at.ac.tuwien.big.autoedit.change.basic.CreateObjectChange;
import at.ac.tuwien.big.autoedit.change.basic.CreateObjectChangeType;
import at.ac.tuwien.big.autoedit.change.basic.DeleteObjectChangeType;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.fixer.ChangeSomething;
import at.ac.tuwien.big.autoedit.fixer.FixAttempt;
import at.ac.tuwien.big.autoedit.fixer.Increase;
import at.ac.tuwien.big.autoedit.fixer.SetAdd;
import at.ac.tuwien.big.autoedit.fixer.SetAddAny;
import at.ac.tuwien.big.autoedit.fixer.SetRemove;
import at.ac.tuwien.big.autoedit.fixer.SetRemoveAny;
import at.ac.tuwien.big.autoedit.oclvisit.AbstractSelectiveEvaluator;
import at.ac.tuwien.big.autoedit.oclvisit.EvalResult;
import at.ac.tuwien.big.autoedit.oclvisit.ExpressionResult;
import at.ac.tuwien.big.autoedit.oclvisit.FixActionMap;
import at.ac.tuwien.big.autoedit.oclvisit.FixingActionGenerator;

public class ApplyAnyAllInstancesChanges  extends AbstractSelectiveEvaluator<OperationCallExp, Collection> implements FixingActionGenerator<OperationCallExp, Collection> {

	public ApplyAnyAllInstancesChanges() {
		super(OperationCallExp.class, Collection.class, false, "allInstances");
	}
	
	public static ApplyAnyAllInstancesChanges INSTANCE = new ApplyAnyAllInstancesChanges();

	@Override
	public boolean addFixingPossibilitiesLocal(MyResource resource, FixAttempt singleAttemptForThis, EvalResult baseres, int realpriority,
			FixActionMap potentialFixChanges) {
		if (!(baseres instanceof ExpressionResult)) {
			return false;
		}
		if (!(singleAttemptForThis instanceof ChangeSomething)) {
			return false;
		}
		ExpressionResult res = (ExpressionResult)baseres;
		Object resultValue = res.getResult();
		EClass allInstanceType = null;
		//TODO: Es gibt wohl was besseres ...
		org.eclipse.ocl.ecore.OperationCallExp opExp = (org.eclipse.ocl.ecore.OperationCallExp) res.getExpression();
		EOperation op = opExp.getReferredOperation();
		EClassifier ecl = op.getEType();
		if (!(ecl instanceof EClass)) {
			System.err.println("Strange operation allInstances type: " + ecl);
			return false;
		}
		EClass cl = (EClass)ecl;
		List<EClass> allInstancableTypes = resource.getInstancibleTypes(cl);
		
		
		DeleteObjectChangeType deleteobject = DeleteObjectChangeType.createObjectFromObjects(resource.getResource(), 
				new ArrayList<>((Collection)resultValue));
		potentialFixChanges.addFixingAction(realpriority, deleteobject);
		
		CreateObjectChangeType co = CreateObjectChangeType.createObjectFromClasses(resource.getResource(), allInstancableTypes);
		potentialFixChanges.addFixingAction(realpriority, co);
		return true;
	}

	@Override
	public int getBasePriority() {
		return FixingActionGenerator.LOCAL_SEARCH_PRIORITY;
	}

}

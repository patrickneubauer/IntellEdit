package at.ac.tuwien.big.autoedit.change.parameter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.change.Parameter;
import at.ac.tuwien.big.autoedit.change.ParameterType;
import at.ac.tuwien.big.autoedit.change.basic.EmptyChangeType;
import at.ac.tuwien.big.autoedit.change.basic.FeatureChangeType;
import at.ac.tuwien.big.autoedit.ecore.util.MyResource;
import at.ac.tuwien.big.autoedit.scope.EqualProbabilitySampler;
import at.ac.tuwien.big.autoedit.scope.EqualProbabilityScope;
import at.ac.tuwien.big.autoedit.scope.ValueScope;
import at.ac.tuwien.big.autoedit.scope.helper.EvalFunc;
import at.ac.tuwien.big.autoedit.scope.helper.GetFunc;

public class ConditionalScopeParameterType<CT extends ChangeType<CT,?>, PType> implements ParameterType<CT, PType> {
	
	private Class<PType> clazz;
	private EvalFunc<CT,List<PType>> evalFunc;
	private EqualProbabilityScope<PType> scope;
	

	public ConditionalScopeParameterType(Class<PType> clazz,
			EvalFunc<CT,List<PType>> eval) {
		this.clazz = clazz;
		this.evalFunc = eval;
		this.scope = EqualProbabilityScope.fromList(Arrays.asList());
	}


	@Override
	public ValueScope<PType, ?> getDefaultScope() {
		scope.setList(evalFunc.eval(null));
		return scope;
	}

	@Override
	public ValueScope<PType, ?> getCurScope(CT self) {
		scope.setList(evalFunc.eval(self));
		return scope;
	}

	@Override
	public Class<PType> getValueClass() {
		return clazz;
	}


}

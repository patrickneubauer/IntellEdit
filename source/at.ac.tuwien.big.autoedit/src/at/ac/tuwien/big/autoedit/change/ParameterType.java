package at.ac.tuwien.big.autoedit.change;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import at.ac.tuwien.big.autoedit.change.parameter.StaticScopeParameterType;
import at.ac.tuwien.big.autoedit.scope.DynamicEqualProbabilityScope;
import at.ac.tuwien.big.autoedit.scope.ValueScope;

public interface ParameterType<Type extends ChangeType<Type,?>, PType> {

	
	public ValueScope<PType,?> getDefaultScope();
	
	public ValueScope<PType,?> getCurScope(Type self);
	
	public Class<PType> getValueClass();

	public static<T extends ChangeType<T,U>,U extends Change<U>,R> ParameterType<T, R> 
		equalProbability(Class<R> clazz, List<R> readList) {
		return StaticScopeParameterType.fromEqualProbabilityList(clazz, readList);
	}
	
	public static<T extends ChangeType<T,U>,U extends Change<U>,R> ParameterType<T, R> 
		equalProbabilityMultiFeat(Class<R> clazz, EObject obj, EStructuralFeature multiFeat) {
		return new StaticScopeParameterType<T, R>(clazz, DynamicEqualProbabilityScope.fromList(()->(List)obj.eGet(multiFeat)));
	}

	public static<T extends ChangeType<T,U>,U extends Change<U>,R> ParameterType<T, R> constant(R obj) {
		return StaticScopeParameterType.fromEqualProbabilityList((Class)((obj==null)?Object.class:obj.getClass()), Collections.singletonList(obj));
	}
	
}

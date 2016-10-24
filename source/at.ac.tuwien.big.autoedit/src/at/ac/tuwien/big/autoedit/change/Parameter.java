package at.ac.tuwien.big.autoedit.change;

import java.util.Iterator;

import at.ac.tuwien.big.autoedit.scope.ValueScope;

/**Der Parameter wird auch für Änderungen vrwendet, die bei Scopes auftreten ?!*/
public interface Parameter<Type extends ChangeType<Type,?>,PType> {
	
	public default ValueScope<PType,?> getDefaultScope() {
		return getType().getDefaultScope();
	}
	
	public default ValueScope<PType,?> getCurScope(Type self) {
		return getType().getCurScope(self);
	}
	
	public default Class<PType> getValueClass() {
		return getType().getValueClass();
	}
	
	public ParameterType<Type,PType> getType();
	
	public default PType getRandom(Type self) {
		ValueScope<PType, ?> scope = getCurScope(self);
		return scope.sampled();
	}
	
	public String getName();
	
	public int getIndex();
	
	public void setName(String name);
	
	public void setIndex(int index);


}

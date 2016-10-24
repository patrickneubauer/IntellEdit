package at.ac.tuwien.big.autoedit.change.parameter;

import at.ac.tuwien.big.autoedit.change.ChangeType;
import at.ac.tuwien.big.autoedit.change.Parameter;
import at.ac.tuwien.big.autoedit.change.ParameterType;

public class SimpleParameter<CT extends ChangeType<CT,?>, PType> implements Parameter<CT, PType> {
	
	private String name;
	private int index;
	private ParameterType<CT, PType> type;
	
	
	public SimpleParameter(ParameterType<CT, PType> type, String name, int index) {
		this.type = type;
		this.index = index;
		this.name = name;
	}

	@Override
	public ParameterType<CT, PType> getType() {
		return type;
	}
	
	public static<CT extends ChangeType<CT,?>, PType> SimpleParameter<CT,PType> fromType(ParameterType<CT,PType> pt, String name, int index) {
		return new SimpleParameter<>(pt,name,index);
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int getIndex() {
		return index;
	}
	
	public void setIndex(int index) {
		this.index = index;
	}

}

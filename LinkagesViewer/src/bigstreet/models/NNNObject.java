package bigstreet.models;

import java.io.Serializable;

public abstract class NNNObject implements Serializable {
	
	//***** DATA MEMBERS *****//
	protected Integer id; 
	protected String name;
	protected String code;
	protected String definition;
	
	//***** METHODS *****//
	public String getName()
	{
		return this.name;
	}
	
	public String getCode()
	{
		return this.code;
	}
	
	public String getDefinition()
	{
		return this.definition;
	}
	
	public int getNumberOfCorrelatedNodes() {
		return 0;
	}
}

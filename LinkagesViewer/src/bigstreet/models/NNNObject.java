package bigstreet.models;

import java.io.Serializable;
import java.util.List;

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

        public abstract List<NNNObject> getPositivelyCorrelatedObjects();
        public abstract List<NNNObject> getNegativelyCorrelatedObjects();
}

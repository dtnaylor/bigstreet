package bigstreet.models;

import java.io.Serializable;
import java.util.List;

public abstract class NNNObject implements Serializable, Comparable {
	
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

        public boolean equals(Object obj){
            return ((NNNObject)obj).getName().trim().equals(name.trim());// && ((Diagnosis)obj).getCode() == code;
	}

        public int compareTo(Object o) {
            return this.getName().compareTo(((NNNObject) o).getName());
        }
}

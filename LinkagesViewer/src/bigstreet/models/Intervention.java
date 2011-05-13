package bigstreet.models;

import java.util.List;

public class Intervention extends NNNObject {
	//***** DATA MEMBERS *****//

	
	//***** CONSTRUCTORS *****//
	public Intervention(int id, String name, String code, String definition)
	{
		this.id = id;
		this.name = name;
		this.code = code;
		this.definition = definition;
	}
	
	//***** METHODS *****//
        @Override
        public String toString() {
            return this.getName();
        }

    @Override
    public List<NNNObject> getPositivelyCorrelatedObjects() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<NNNObject> getNegativelyCorrelatedObjects() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

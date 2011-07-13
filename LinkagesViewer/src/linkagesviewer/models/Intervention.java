package linkagesviewer.models;

import java.util.ArrayList;
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
        List<NNNObject> positivelyCorrelatedObjects = new ArrayList<NNNObject>();



        return positivelyCorrelatedObjects;
    }

    @Override
    public List<NNNObject> getNegativelyCorrelatedObjects() {
        List<NNNObject> negativelyCorrelatedObjects = new ArrayList<NNNObject>();

        

        return negativelyCorrelatedObjects;
    }

}

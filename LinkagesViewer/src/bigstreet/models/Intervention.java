package bigstreet.models;

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

}

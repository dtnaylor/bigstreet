package edu.uiowa.nursing.models;

public class Intervention {
	//***** DATA MEMBERS *****//
	private int id;
	private String name;
	private String code;
	private String definition;
	
	//***** CONSTRUCTORS *****//
	public Intervention(int id, String name, String code, String definition)
	{
		this.id = id;
		this.name = name;
		this.code = code;
		this.definition = definition;
	}
	
	//***** METHODS *****//
	public String getName()
	{
		return name;
	}
	
	public String getCode()
	{
		return code;
	}
	
	public String getDefinition()
	{
		return definition;
	}
}

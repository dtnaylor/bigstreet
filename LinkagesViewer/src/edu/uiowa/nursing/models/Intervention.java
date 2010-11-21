package edu.uiowa.nursing.models;

public class Intervention {
	//***** DATA MEMBERS *****//
	private int id;
	private String name;
	private int code;
	private String definition;
	
	//***** CONSTRUCTORS *****//
	public Intervention(int id, String name, int code, String definition)
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
	
	public int getCode()
	{
		return code;
	}
	
	public String getDefinition()
	{
		return definition;
	}
}

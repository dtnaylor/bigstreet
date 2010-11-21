package edu.uiowa.nursing.models;

public class Intervention {
	//***** DATA MEMBERS *****//
	private String name;
	private int code;
	
	//***** CONSTRUCTORS *****//
	public Intervention(String name, int code)
	{
		this.name = name;
		this.code = code;
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
}

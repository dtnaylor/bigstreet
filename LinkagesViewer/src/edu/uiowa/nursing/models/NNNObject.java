package edu.uiowa.nursing.models;

public abstract class NNNObject {
	
	//***** DATA MEMBERS *****//
	protected Integer id; 
	protected String name;
	protected String code;
	protected String definition;
	protected int numCorrelatedNodesToShow;
	
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
	
	public int getNumCorrelatedNodesToShow()
	{
		return this.numCorrelatedNodesToShow;
	}
	
	public void setNumCorrelatedNodesToShow(int numToShow)
	{
		this.numCorrelatedNodesToShow = numToShow;
	}
}

package edu.uiowa.nursing.models;

import java.util.ArrayList;
import java.util.List;

public class Outcome {
	//***** DATA MEMBERS *****//
	private String name;
	private int code;
	private String definition;
	private List<Intervention> majorInterventions;
	private List<Intervention> suggestedInterventions;
	private List<Intervention> optionalInterventions;
	
	//***** CONSTRUCTORS *****//
	public Outcome(String name, int code, String definition)
	{
		this.name = name;
		this.code = code;
		this.definition = definition;
		this.majorInterventions = new ArrayList<Intervention>();
		this.suggestedInterventions = new ArrayList<Intervention>();
		this.optionalInterventions = new ArrayList<Intervention>();
	}
	
	public Outcome(String name, int code, String definition, List<Intervention> majorInterventions,
			List<Intervention> suggestedInterventions, List<Intervention> optionalInterventions)
	{
		this.name = name;
		this.code = code;
		this.definition = definition;
		this.majorInterventions = majorInterventions;
		this.suggestedInterventions = suggestedInterventions;
		this.optionalInterventions = optionalInterventions;
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
	
	public List<Intervention> getMajorInterventions()
	{
		return majorInterventions;
	}
	
	public List<Intervention> getSuggestedInterventions()
	{
		return suggestedInterventions;
	}
	
	public List<Intervention> getOptionalInterventions()
	{
		return optionalInterventions;
	}
	
	public void addMajorIntervention(Intervention intervention)
	{
		majorInterventions.add(intervention);
	}
	
	public void addSuggestedIntervention(Intervention intervention)
	{
		suggestedInterventions.add(intervention);
	}
	
	public void addOptionalIntervention(Intervention intervention)
	{
		optionalInterventions.add(intervention);
	}
	
	public List<Intervention> getInterventions(EdgeType type)
	{
		switch(type)
		{
			case MAJOR_INTERVENTION:
				return getMajorInterventions();
			case SUGGESTED_INTERVENTION:
				return getSuggestedInterventions();
			case OPTIONAL_INTERVENTION:
				return getOptionalInterventions();
			default:
				throw new RuntimeException("Invalid intervention type: " + type.toString());
		}
	}
}

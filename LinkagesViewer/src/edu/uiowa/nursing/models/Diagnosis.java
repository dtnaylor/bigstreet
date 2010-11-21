package edu.uiowa.nursing.models;

import java.util.ArrayList;
import java.util.List;

import edu.uiowa.nursing.controllers.AppController;

public class Diagnosis {
	//***** DATA MEMBERS *****//
	private String name;
	private int code;
	private String definition;
	private List<Outcome> outcomes;
	private List<Diagnosis> correlatedDiagnoses;
	
	//***** CONSTRUCTORS *****//	
	public Diagnosis(String name, int code, String definition, List<Outcome> outcomes)
	{
		this.name = name;
		this.code = code;
		this.definition = definition;
		this.outcomes = outcomes;
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
	
	public List<Outcome> getOutcomes()
	{
		return outcomes;
	}
	
	public void addOutcome(Outcome outcome)
	{
		outcomes.add(outcome);
	}
	
	public List<Diagnosis> getCorrelatedDiagnoses()
	{
		//If we haven't already pulled this info from the DB, get it now
		if(correlatedDiagnoses == null)
		{
			//TODO: do this for real
			correlatedDiagnoses = new ArrayList<Diagnosis>();
			for (int i = 1; i < 10; i++)
			{
				correlatedDiagnoses.add(AppController.getDiagnoses().get(i));
			}
		}
		
		return correlatedDiagnoses;
	}
	
	public boolean equals(Object obj)
	{
		return ((Diagnosis)obj).getName().trim().equals(name.trim());// && ((Diagnosis)obj).getCode() == code;
	}
}
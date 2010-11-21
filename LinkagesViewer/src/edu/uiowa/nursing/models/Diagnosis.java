package edu.uiowa.nursing.models;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uiowa.nursing.configuration.DBConnection;
import edu.uiowa.nursing.controllers.AppController;

public class Diagnosis {
	//***** DATA MEMBERS *****//
	private Integer id; 
	private String name;
	private String code;
	private String definition;
	private List<Outcome> outcomes;
	private List<Diagnosis> correlatedDiagnoses;
	
	//***** CONSTRUCTORS *****//	
	public Diagnosis(int id, String name, String code, String definition)
	{
		this.id = id; 
		this.name = name;
		this.code = code;
		this.definition = definition;
		this.outcomes = outcomes;
	}
	
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
	
	public List<Outcome> getOutcomes()
	{
		/*  assume that the list of outcomes will never be empty
			and therefore if the list of outcomes is empty that
			it just hasn't been populated. worse case scenario
			if that assumption is ever broken, we'll just end up
			pulling an empty record set from the database server
			a couple of extra times -- we wouldn't get *bad* data.
		*/
		if (this.outcomes.size() == 0) {
			try {
				String query = new StringBuffer()
					.append("SELECT id, noc_code, isnull(name_current,name_2005) FROM [dbo].[outcomes] o")
					.append("JOIN diagnosis_outcomes do")
					.append("ON o.id = do.outcome_id")
					.append("WHERE do.diagnosis_id=")
					.append(this.id.toString())
					.toString();
				
				ResultSet rs = DBConnection.connection.createStatement().executeQuery(query);
				while (rs.next()) {
					this.outcomes.add(Outcome(rs.getString("name"),
											  rs.sgetString()))
				}
				
			}
		}

			    while (rs_d.next()) {
			    	int id_d = rs_d.getInt("id");
			    	String name_d = rs_d.getString("name");
			    	String def_d = rs_d.getString("definition");
			    	String code_d = rs_d.getString("nanda_code");
			    	
			    	//System.out.println(name_d + id_d);
			    	
			    	// Get outcomes
			    	List<Outcome> outcomes = new ArrayList<Outcome>();
			    	String SQL_o = "SELECT * FROM linkagesbook_outcomes_corrected WHERE diagnosis_id=" + id_d;
				    Statement stmt_o;
					stmt_o = con.createStatement();
					ResultSet rs_o = stmt_o.executeQuery(SQL_o);
				
			
		}
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
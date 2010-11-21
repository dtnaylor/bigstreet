package edu.uiowa.nursing.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		this.outcomes = new ArrayList<Outcome>();
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
					.append("SELECT id, noc_code, isnull(name_current,name_2005) as name, definition FROM [dbo].[outcomes] o ")
					.append("JOIN diagnosis_outcomes do ")
					.append("ON o.id = do.outcome_id ")
					.append("WHERE do.diagnosis_id=")					
					.append(this.id.toString())
					.toString();
				
				ResultSet rs = DBConnection.connection.createStatement().executeQuery(query);
				// public Outcome(int id, int parentID, String name, String code, String definition)
				while (rs.next()) 
					this.outcomes.add(new Outcome(rs.getInt("id"),
											  this.id,
											  rs.getString("name"),
											  rs.getString("noc_code"),
											  rs.getString("definition"))); 
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return this.outcomes;
	}
	
	public void addOutcome(Outcome outcome)
	{
		outcomes.add(outcome);
	}
	
	public List<Diagnosis> getCorrelatedDiagnoses() {
		//If we haven't already pulled this info from the DB, get it now
		if (correlatedDiagnoses == null) {
			correlatedDiagnoses = new ArrayList<Diagnosis>();
			try {
				String query = "SELECT id, nanda_code, isnull(name_current,name_2005) as name, definition FROM diagnoses d join correlations_between_diagnoses cbd on d.id = cbd.diagnosis_id_b WHERE cbd.diagnosis_id_a = ? order by correlation desc";
				PreparedStatement search_ps = DBConnection.connection.prepareStatement(query);
				search_ps.setInt(1, this.id);
				ResultSet rs = search_ps.executeQuery();
				while (rs.next()) {
					correlatedDiagnoses.add(new Diagnosis(rs.getInt("id"),
														  rs.getString("name"),
														  rs.getString("nanda_code"),
														  rs.getString("definition")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return correlatedDiagnoses;
	}
	
	public boolean equals(Object obj)
	{
		return ((Diagnosis)obj).getName().trim().equals(name.trim());// && ((Diagnosis)obj).getCode() == code;
	}
}
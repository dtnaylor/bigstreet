package edu.uiowa.nursing.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.uiowa.nursing.configuration.DBConnection;

public class Outcome {
	//***** DATA MEMBERS *****//
	private int id;
	private int parentID;
	private String name;
	private String code;
	private String definition;
	private List<Intervention> majorInterventions;
	private List<Intervention> suggestedInterventions;
	private List<Intervention> optionalInterventions;
	
	//***** CONSTRUCTORS *****//
	public Outcome(int id, int parentID, String name, String code, String definition)
	{
		this.id = id;
		this.parentID = parentID;
		this.name = name;
		this.code = code;
		this.definition = definition;
		this.majorInterventions = getInterventionsFromDB(EdgeType.MAJOR_INTERVENTION);
		this.suggestedInterventions = getInterventionsFromDB(EdgeType.SUGGESTED_INTERVENTION);
		this.optionalInterventions = getInterventionsFromDB(EdgeType.OPTIONAL_INTERVENTION);
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
	
	private List<Intervention> getInterventionsFromDB(EdgeType type)
	{
		String typeString;
		switch(type)
		{
			case MAJOR_INTERVENTION:
				typeString = "major";
			case SUGGESTED_INTERVENTION:
				typeString = "suggested";
			case OPTIONAL_INTERVENTION:
				typeString = "optional";
		}
		
		
		// Get major interventions
		List<Intervention> interventions = new ArrayList<Intervention>();
		
		String sql = new StringBuffer()
			.append("SELECT id, isnull(name_current,name_2005) as name, nic_code, [definition], [type]")
			.append("FROM [dbo].[interventions] i JOIN [dbo].[diagnosis_outcome_interventions] doi")
			.append("ON i.id = doi.intervention_id")
			.append("WHERE diagnosis_id = " + parentID + " and outcome_id = " + id)
			.toString();
		
		Statement stmt;
		try {
			stmt = DBConnection.connection.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				String id = rs.getString("id");
				String name = rs.getString("name");
				String code = rs.getString("nic_code");
				String definition = rs.getString("definition");
				
				if (code == null) code = "-1";
				
				interventions.add(new Intervention(Integer.parseInt(id), name, code, definition));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return interventions;
	}
}

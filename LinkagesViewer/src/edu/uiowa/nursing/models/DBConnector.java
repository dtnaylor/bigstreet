package edu.uiowa.nursing.models;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBConnector {
	private Connection con;

	public DBConnector(String address, String username, String password, String database) {
		
	
		//at some point call con.close()
	}
	
	public void close() {
		
	}

	
	public HashMap<Integer, Diagnosis> getDiagnoses() {
		HashMap<Integer, Diagnosis> diagnoses = new HashMap<Integer, Diagnosis>();
		
		try {
			
			// Get diagnoses
			String SQL_d = "SELECT * FROM linkagesbook_diagnoses_corrected";
		    Statement stmt_d;
			stmt_d = con.createStatement();
			ResultSet rs_d = stmt_d.executeQuery(SQL_d);

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
				
				while (rs_o.next()) {
					int id_o = rs_o.getInt("id");
					String name_o = rs_o.getString("name");
					String def_o = rs_o.getString("definition");
					String code_o = rs_o.getString("noc_code");
					 
					//System.out.println("\t" + name_o + code_o);
					 
					// Get major interventions
					List<Intervention> major_interventions = new ArrayList<Intervention>();
					String SQL_i = "SELECT * FROM linkagesbook_interventions_corrected WHERE outcome_id=" + id_o + " AND type='major'";
					Statement stmt_i;
					stmt_i = con.createStatement();
					ResultSet rs_i = stmt_i.executeQuery(SQL_i);
						
					while (rs_i.next()) {
						String name_i = rs_i.getString("name");
						String code_i = rs_i.getString("nic_code");
						
						if (code_i == null) code_i = "-1";
						
						major_interventions.add(new Intervention(name_i, Integer.parseInt(code_i)));
					}
					rs_i.close();
					stmt_i.close();

					
					// Get suggested interventions
					List<Intervention> suggested_interventions = new ArrayList<Intervention>();
					SQL_i = "SELECT * FROM linkagesbook_interventions_corrected WHERE outcome_id=" + id_o + " AND type='suggested'";
					stmt_i = con.createStatement();
					rs_i = stmt_i.executeQuery(SQL_i);
						
					while (rs_i.next()) {
						String name_i = rs_i.getString("name");
						String code_i = rs_i.getString("nic_code");
						
						if (code_i == null) code_i = "-1";
						
						suggested_interventions.add(new Intervention(name_i, Integer.parseInt(code_i)));
					}
					rs_i.close();
					stmt_i.close();
					
					// Get optional interventions
					List<Intervention> optional_interventions = new ArrayList<Intervention>();
					SQL_i = "SELECT * FROM linkagesbook_interventions_corrected WHERE outcome_id=" + id_o + " AND type='optional'";
					stmt_i = con.createStatement();
					rs_i = stmt_i.executeQuery(SQL_i);
						
					while (rs_i.next()) {
						String name_i = rs_i.getString("name");
						String code_i = rs_i.getString("nic_code");
						
						if (code_i == null) code_i = "-1";
						
						optional_interventions.add(new Intervention(name_i, Integer.parseInt(code_i)));
					}
					rs_i.close();
					stmt_i.close();
					
					if (code_o == null) code_o = "-1";
				
					//outcomes.add(new Outcome(name_o, Integer.parseInt(code_o), def_o, major_interventions, suggested_interventions, optional_interventions));
				}
				rs_o.close();
				stmt_o.close();

				diagnoses.put(Integer.parseInt(code_d), new Diagnosis(name_d, Integer.parseInt(code_d), def_d, outcomes));
		    }
		    rs_d.close();
		    stmt_d.close();
		    
		    
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return diagnoses;
	}
}

    package bigstreet.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bigstreet.configuration.DBConnection;

public class Diagnosis extends NNNObject {
	//***** DATA MEMBERS *****//
	
	private List<Outcome> outcomes;
	private List<Diagnosis> correlatedDiagnoses;
	private List<Diagnosis> negativelyCorrelatedDiagnoses;
	private List<Outcome> correlatedOutcomes;
	private List<Outcome> negativelyCorrelatedOutcomes;
	
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
	public List<Outcome> getOutcomes()
	{
		/*  assume that the list of outcomes will never be empty
			and therefore if the list of outcomes is empty that
			it just hasn't been populated. worse case scenario
			if that assumption is ever broken, we'll just end up
			pulling an empty record set from the database server
			a couple of extra times -- we wouldn't get *bad* data.
		*/
                this.outcomes.clear();
		if (this.outcomes.isEmpty()) {
			try {
				String query = new StringBuffer()
					.append("SELECT id, noc_code, name, definition FROM outcomes o ")
					.append("JOIN diagnosis_outcomes do ")
					.append("ON o.id = do.outcome_id ")
					.append("WHERE do.diagnosis_id=")					
					.append(this.id.toString())
                                        .append(" ORDER BY NAME")
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
        
        public List<Outcome> getCorrelatedOutcomes()
	{
                this.outcomes.clear();
		if (this.outcomes.isEmpty()) {
			try {
				String query = new StringBuffer()
					.append("SELECT id, noc_code, name, definition FROM outcomes o ")
					.append("JOIN diagnosis_outcome_correlations do ")
					.append("ON o.id = do.outcome_id ")
					.append("WHERE do.diagnosis_id=")					
					.append(this.id.toString())
                                        .append(" AND correlation > 0")
                                        .append(" ORDER By correlation DESC")
					.toString();
				
				ResultSet rs = DBConnection.connection.createStatement().executeQuery(query);
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
	
	public int getNumberOfCorrelatedNodes() {
		try {
			String query = "select count(*) as count FROM correlations_between_diagnoses where diagnosis_id_a = ?";
			PreparedStatement search_ps = DBConnection.connection.prepareStatement(query);
			search_ps.setInt(1, this.id);
			ResultSet rs = search_ps.executeQuery();
			rs.next();
			return rs.getInt("count");
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(1);
			return 0;
		}
	}
	
	public List<Diagnosis> getCorrelatedDiagnoses() {
		//If we haven't already pulled this info from the DB, get it now
		if (correlatedDiagnoses == null) {
			correlatedDiagnoses = new ArrayList<Diagnosis>();
			try {
				String query = "SELECT id, nanda_code, name, definition FROM diagnoses d join correlations_between_diagnoses cbd on d.id = cbd.diagnosis_id_b WHERE cbd.diagnosis_id_a = ? AND correlation > 0 order by correlation desc";
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
	
	public List<Diagnosis> getNegativelyCorrelatedDiagnoses() {
		//If we haven't already pulled this info from the DB, get it now
		if (negativelyCorrelatedDiagnoses == null) {
			negativelyCorrelatedDiagnoses = new ArrayList<Diagnosis>();
			try {
				String query = "SELECT id, nanda_code, name, definition FROM diagnoses d join correlations_between_diagnoses cbd on d.id = cbd.diagnosis_id_b WHERE cbd.diagnosis_id_a = ? AND correlation < 0 order by correlation desc";
				PreparedStatement search_ps = DBConnection.connection.prepareStatement(query);
				search_ps.setInt(1, this.id);
				ResultSet rs = search_ps.executeQuery();
				while (rs.next()) {
					negativelyCorrelatedDiagnoses.add(new Diagnosis(rs.getInt("id"),
														  rs.getString("name"),
														  rs.getString("nanda_code"),
														  rs.getString("definition")));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return negativelyCorrelatedDiagnoses;
	}
	
	// TODO: implement
	public List<Outcome> getNegativelyCorrelatedOutcomes() {
		//If we haven't already pulled this info from the DB, get it now
		if (negativelyCorrelatedOutcomes == null) {
			negativelyCorrelatedOutcomes = new ArrayList<Outcome>();
                        try {
				String query = new StringBuffer()
					.append("SELECT id, noc_code, name, definition FROM outcomes o ")
					.append("JOIN diagnosis_outcome_correlations do ")
					.append("ON o.id = do.outcome_id ")
					.append("WHERE do.diagnosis_id=")
					.append(this.id.toString())
                                        .append(" AND correlation < 0")
                                        .append(" ORDER By correlation DESC")
					.toString();

				ResultSet rs = DBConnection.connection.createStatement().executeQuery(query);
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
		return negativelyCorrelatedOutcomes;
	}
	

        @Override
        public String toString() {
            return this.getName();
        }

    @Override
    public List<NNNObject> getPositivelyCorrelatedObjects() {
        List<NNNObject> positivelyCorrelatedObjects = new ArrayList<NNNObject>();

        positivelyCorrelatedObjects.addAll(getCorrelatedDiagnoses());
        positivelyCorrelatedObjects.addAll(getCorrelatedOutcomes());

        return positivelyCorrelatedObjects;
    }

    @Override
    public List<NNNObject> getNegativelyCorrelatedObjects() {
        List<NNNObject> negativelyCorrelatedObjects = new ArrayList<NNNObject>();

        negativelyCorrelatedObjects.addAll(getNegativelyCorrelatedDiagnoses());
        negativelyCorrelatedObjects.addAll(getNegativelyCorrelatedOutcomes());

        return negativelyCorrelatedObjects;
    }
}
package bigstreet.controllers;

import bigstreet.BigStreetView;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.DefaultListModel;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import bigstreet.models.Diagnosis;
import bigstreet.models.GraphNode;
import bigstreet.models.NNNGraph;
import bigstreet.models.NodeType;
import bigstreet.models.Outcome;
import bigstreet.views.MainWindow;
import bigstreet.configuration.*;
import bigstreet.models.NNNObject;

public abstract class AppController {

	//***** DATA MEMBERS *****//
	public static Dimension WINDOW_SIZE = new Dimension(1024, 768);
	public static Dimension GRAPH_SIZE = new Dimension(1024, 768);
	
	//private static MainWindow mainWindow;
        private static BigStreetView mainWindow;
	
	private static HashMap<Integer, Diagnosis> diagnoses;
	
	public static DefaultListModel searchResults = new DefaultListModel();
        public static DefaultListModel correlatedDiagnoses = new DefaultListModel();
        public static DefaultListModel correlatedOutcomes = new DefaultListModel();

	private static HashMap<Integer, Integer> searchResultCodes = new HashMap<Integer, Integer>();
	private static HashMap<Integer, Diagnosis> searchResultObjects = new HashMap<Integer, Diagnosis>();
	
	private static NNNGraph graphToDisplay;
	//private static GraphNode currentNode;
	private static List<GraphNode> selectedNodes = new ArrayList<GraphNode>();
	
	//***** PROPERTIES *****//
        public static void setMainWindow(BigStreetView view) {
            mainWindow = view;
            mainWindow.setGraphView(graphToDisplay.getView());
        }

	public static HashMap<Integer, Diagnosis> getDiagnoses() {
		return diagnoses;
	}
	
	public static NNNGraph getGraphToDisplay() {
		return AppController.graphToDisplay;
	}
	
	public static void setDiagnosesToDisplay(List<Diagnosis> diagnosesToDisplay)
	{
		//AppController.graphToDisplay = new NNNGraph(diagnosesToDisplay);
	}
	
	public static void addDiagnosisToDisplay(Diagnosis diagnosis)
	{
		AppController.graphToDisplay.addDiagnosis(diagnosis);
	}
	
	public static void setSelectedNode(GraphNode node)
	{
		if (selectedNodes == null) selectedNodes = new ArrayList<GraphNode>();
		
		if (!selectedNodes.isEmpty())
		{
			for (GraphNode n : selectedNodes){
				n.setSelected(false);
			}
		}
		
		selectedNodes.clear();
		selectedNodes.add(node);
		node.setSelected(true);
                //AppController.mainWindow.setSelectedNodeInfo(node);
		mainWindow.setSelectedNodeName(node);
                mainWindow.setSelectedNodeDescription(node);


                Diagnosis n = ((Diagnosis) node.getNNNObject());

                // Set correlated diagnoses
                for (Diagnosis d : n.getCorrelatedDiagnoses())  {
                    correlatedDiagnoses.addElement(d);
                }
                mainWindow.setCorrelatedDiagnosises(correlatedDiagnoses);

                // Set outcomes to be displayed..
                for (Outcome o: n.getOutcomes()) {
                    correlatedOutcomes.addElement(o);
                }
                mainWindow.setLinkedOutcomes(correlatedOutcomes);

	}
	
	public static void addSelectedNode(GraphNode node)
	{
		if (selectedNodes == null) selectedNodes = new ArrayList<GraphNode>();
		
		selectedNodes.add(node);
		node.setSelected(true);
		
		mainWindow.displayNodeInfo("Multiple selections");
	}
	
	public static List<GraphNode> getSelectedNodes() {
		return selectedNodes;
	}
	

	//***** METHODS *****//
	public static void startApp() {
		// Open connection to database
		DBConnection.openConnection();
		graphToDisplay = new NNNGraph();
	}
	
	public static void graphUpdated()
	{
		mainWindow.update();
	}
	
	public static void setMouseMode(ModalGraphMouse.Mode mode)
	{
		graphToDisplay.setMouseMode(mode);
	}
	
	public static void zoomIn()
	{
		graphToDisplay.zoomIn();
	}
	
	public static void zoomOut()
	{
		graphToDisplay.zoomOut();
	}
	
//	public static void removeSelectedNode()
//	{
//		// Right now we can only remove diagnoses
//		if (currentNode.getType() == NodeType.DIAGNOSIS)
//		{
//			graphToDisplay.removeDiagnoses(Arrays.asList((Diagnosis)currentNode.getNNNObject()));
//			currentNode = null;
//		}
//	}
	
	public static void saveScreenShot(String path)
	{
		graphToDisplay.saveScreenShot(path);
	}
	
	public static DefaultListModel search(String text) {
            // Clear previous search results
            searchResults.clear();
            searchResultObjects.clear();

            try {
                    String query = "SELECT id, nanda_code, isnull(name_current,name_2005) as name, definition FROM DIAGNOSES WHERE NAME_CURRENT LIKE ? or NAME_2005 LIKE ?";
                    PreparedStatement search_ps = DBConnection.connection.prepareStatement(query);
                    search_ps.setString(1,"%"+text+"%");
                    search_ps.setString(2,"%"+text+"%");
                    ResultSet rs = search_ps.executeQuery();
                    while (rs.next()) {
                            searchResults.addElement(rs.getString("name"));
                            searchResultObjects.put(searchResults.size() - 1, new Diagnosis(
                                rs.getInt("id"),
                                rs.getString("name"),														rs.getString("nanda_code"),
                                rs.getString("definition")));
                    }
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    System.exit(1);
            }
            return searchResults;
	}
	
	public static void addDiagnosis(int searchResultIndex)
	{
		addDiagnosisToDisplay(searchResultObjects.get(searchResultIndex));
	}

            public static HashMap<Integer, Diagnosis> get_searchResultObjects() {
            return searchResultObjects;
        }

}

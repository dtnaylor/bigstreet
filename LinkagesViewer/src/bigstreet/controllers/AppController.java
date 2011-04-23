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
import bigstreet.models.Intervention;
import bigstreet.models.NNNObject;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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

        private static HashMap<String, NNNObject> popupMenuNodes = new HashMap<String, NNNObject>();
	
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

        public static void addOutcomeToDisplay(Outcome outcome)
        {
                AppController.graphToDisplay.addOutcome(outcome);
        }

        public static void addInterventionToDisplay(Intervention intervention)
        {
                AppController.graphToDisplay.addIntervention(intervention);
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
                mainWindow.enableCurrentSelectedNodePanel();
                mainWindow.clearCurrentSelectionTabs();
                String type = node.getNNNObject().getClass().getName();
                System.out.println(type);
                if (type.equals("bigstreet.models.Diagnosis")) {
                    mainWindow.addTabsForDiagnosis();
                    Diagnosis n = ((Diagnosis) node.getNNNObject());
                    // populate correlated Diagnoses
                    correlatedDiagnoses.removeAllElements();
                    for (Diagnosis d : n.getCorrelatedDiagnoses())  {
                        correlatedDiagnoses.addElement(d);
                    }
                    mainWindow.setCorrelatedDiagnosises(correlatedDiagnoses);
                    AppController.setOutcomesForSelectedDiagnosis(n, "Linked Outcomes");
                } else if (type.equals("bigstreet.models.Outcome")) {
                    mainWindow.addTabsForOutcome();
                } else {
                    mainWindow.addTabsForIntervention();
                }

	}

        public static void setOutcomesForSelectedDiagnosis(Diagnosis n, String filter) {
                correlatedOutcomes.removeAllElements();
                if (filter.equals("Linked Outcomes") || filter.equals("All Outcomes")) {
                    for (Outcome o: n.getOutcomes()) {
                        correlatedOutcomes.addElement(o);
                    }
                    mainWindow.setLinkedOutcomes(correlatedOutcomes);
                }
                if (filter.equals("Correlated Outcomes") || filter.equals("All Outcomes")) {
                    for (Outcome o: n.getCorrelatedOutcomes()) {
                        correlatedOutcomes.addElement(o);
                    }
                }
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

        public static GraphNode getLastSelectedNode() {
            return selectedNodes.get(selectedNodes.size()-1);
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
                mainWindow.setMouseMode(mode);
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

        public static void saveCurrentView(String filename)
        {
        Object[] stateToSave = {graphToDisplay, GraphNode.getCurrentLocationInformation()};

        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try
        {
            fos = new FileOutputStream(filename);
            out = new ObjectOutputStream(fos);
            out.writeObject(stateToSave);
            out.close();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void loadSavedView(String filename)
    {
        Object[] savedState;

        FileInputStream fis = null;
        ObjectInputStream in = null;
        try
        {
          fis = new FileInputStream(filename);
          in = new ObjectInputStream(fis);
          savedState = (Object[])in.readObject();
          graphToDisplay = (NNNGraph)savedState[0];
          GraphNode.setCurrentLocationInformation((Point2D[])savedState[1]);
          mainWindow.update();
          in.close();
        }
        catch(IOException ex)
        {
          ex.printStackTrace();
        }
        catch(ClassNotFoundException ex)
        {
          ex.printStackTrace();
        }
    }

    public static void showPopupMenu(MouseEvent e, GraphNode n)
    {
        popupMenuNodes.clear();
        List<String> nodeNames = new ArrayList<String>();

        if (n.getType() == NodeType.DIAGNOSIS)
        {
            for (Outcome o : ((Diagnosis)n.getNNNObject()).getOutcomes())
            {
                popupMenuNodes.put(o.getName(), o);
                nodeNames.add(o.getName());
            }
        }
        else if (n.getType() == NodeType.OUTCOME)
        {
            nodeNames.add("MAJOR");
            for (Intervention i : ((Outcome)n.getNNNObject()).getMajorInterventions())
            {
                popupMenuNodes.put(i.getName(), i);
                nodeNames.add(i.getName());
            }
            nodeNames.add("SUGGESTED");
            for (Intervention i : ((Outcome)n.getNNNObject()).getSuggestedInterventions())
            {
                popupMenuNodes.put(i.getName(), i);
                nodeNames.add(i.getName());
            }
            nodeNames.add("OPTIONAL");
            for (Intervention i : ((Outcome)n.getNNNObject()).getOptionalInterventions())
            {
                popupMenuNodes.put(i.getName(), i);
                nodeNames.add(i.getName());
            }
        }

        mainWindow.showPopupMenu(e.getX(), e.getY(), nodeNames);
    }

    public static void addNodeFromPopupMenu(String nodeName)
    {
        NNNObject nnnObj = popupMenuNodes.get(nodeName);

        if(nnnObj == null)
            return;
        else if (nnnObj instanceof Outcome)
            addOutcomeToDisplay((Outcome)nnnObj);
        else if (nnnObj instanceof Intervention)
            addInterventionToDisplay((Intervention)nnnObj);
    }
}

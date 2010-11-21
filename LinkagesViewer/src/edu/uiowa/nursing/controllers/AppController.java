package edu.uiowa.nursing.controllers;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.swing.DefaultListModel;

import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uiowa.nursing.XMLParser;
import edu.uiowa.nursing.models.DBConnector;
import edu.uiowa.nursing.models.Diagnosis;
import edu.uiowa.nursing.models.NNNGraph;
import edu.uiowa.nursing.models.NNNNode;
import edu.uiowa.nursing.models.NodeType;
import edu.uiowa.nursing.views.MainWindow;

public abstract class AppController {

	//***** DATA MEMBERS *****//
	public static Dimension WINDOW_SIZE = new Dimension(1024, 768);
	public static Dimension GRAPH_SIZE = new Dimension(1500, 768);
	
	private static MainWindow mainWindow;
	
	private static String dbAddress, dbUsername, dbPassword, dbDatabase;
	
	private static HashMap<Integer, Diagnosis> diagnoses;
	
	public static DefaultListModel searchResults = new DefaultListModel();
	private static HashMap<Integer, Integer> searchResultCodes = new HashMap<Integer, Integer>();
	
	private static NNNGraph graphToDisplay;
	private static NNNNode currentNode;
	
	private static int numCorrelatedNodesToShow = 0;
	
	//***** PROPERTIES *****//
	public static HashMap<Integer, Diagnosis> getDiagnoses() {
		return diagnoses;
	}
	
	public static NNNGraph getGraphToDisplay() {
		return AppController.graphToDisplay;
	}
	
	public static void setDiagnosesToDisplay(List<Diagnosis> diagnosesToDisplay)
	{
		AppController.graphToDisplay = new NNNGraph(diagnosesToDisplay);
	}
	
	public static void setDiagnosisToDisplay(Diagnosis diagnosis)
	{
		AppController.graphToDisplay = new NNNGraph(diagnosis);
	}
	
	public static void addDiagnosisToDisplay(Diagnosis diagnosis)
	{
		AppController.graphToDisplay.addDiagnoses(Arrays.asList(diagnosis));
	}
	
	public static void setCurrentNode(NNNNode node)
	{
		currentNode = node;
		mainWindow.displayNodeInfo(node);
	}
	
	public static int getNumCorrelatedNodesToShow()
	{
		return numCorrelatedNodesToShow;
	}
	
	public static void setNumCorrelatedNodesToShow(int num)
	{
		numCorrelatedNodesToShow = num;
		
		graphToDisplay.updateCorrelatedNodes(num);
		
		//TODO: Do this for outcomes & interventions too
	}
	
	
	//***** METHODS *****//
	public static void main(String[] args) {
		
		// Read conf file
		getDBInfo();
		
		// Get diagnosis data
		diagnoses = (new XMLParser()).parseDocument();
		//diagnoses = (new DBConnector(dbAddress, dbUsername, dbPassword, dbDatabase)).getDiagnoses();
		
		mainWindow = new MainWindow();
		mainWindow.setVisible(true);
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
	
	public static void saveScreenShot(String path)
	{
		graphToDisplay.saveScreenShot(path);
	}
	
	public static void search(String text)
	{
		// Clear previous search results
		searchResults.clear();
		searchResultCodes.clear();
		
		// Add new results
		for (int code : AppController.getDiagnoses().keySet())
		{
			Diagnosis d = AppController.getDiagnoses().get(code);
			if(d.getName().toLowerCase().contains(text.toLowerCase()))
			{
				searchResults.addElement(d.getName());
				searchResultCodes.put(searchResults.size() - 1, code);
			}
		}
	}

	public static void displayDiagnosis(int searchResultIndex)
	{
		setDiagnosisToDisplay(diagnoses.get(searchResultCodes.get(searchResultIndex)));
	}
	
	public static void addDiagnosis(int searchResultIndex)
	{
		addDiagnosisToDisplay(diagnoses.get(searchResultCodes.get(searchResultIndex)));
	}
	
	private static void getDBInfo()
	{
		try {
			  String homeDir = System.getProperty("user.home");
		      BufferedReader input =  new BufferedReader(new FileReader(homeDir + "/Documents/nnndb.conf"));
		      try {
		        String line = null;
		        while (( line = input.readLine()) != null){
		        	String[] splitLine = line.split("=");
		        	if(splitLine[0].equals("address"))
		        		dbAddress = splitLine[1];
		        	else if (splitLine[0].equals("username"))
		        		dbUsername = splitLine[1];
		        	else if (splitLine[0].equals("password"))
		        		dbPassword = splitLine[1];
		        	else if (splitLine[0].equals("database"))
		        		dbDatabase = splitLine[1];
		        }
		      }
		      finally {
		        input.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
	}
}

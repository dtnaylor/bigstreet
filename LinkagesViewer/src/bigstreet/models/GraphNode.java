package bigstreet.models;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import bigstreet.controllers.AppController;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import java.io.Serializable;

public class GraphNode implements Serializable {

	//***** STATIC *****//
	private static double leftBuffer = 150, rightBuffer = 20;
	private static double columnSpacing = ((AppController.GRAPH_SIZE.width - rightBuffer - leftBuffer) / 3);
	private static double diagnosisSpacing = 75, outcomeSpacing = 50, interventionSpacing = 25;
	private static double diagnosisColumn = leftBuffer;
	private static double outcomeColumn = diagnosisColumn + 1 * columnSpacing;
	private static double interventionColumn1 = diagnosisColumn + 2 * columnSpacing;
	private static double interventionColumn2 = diagnosisColumn + 3 * columnSpacing;
	private static Point2D currentDiagnosisLoc = new Point2D.Double(diagnosisColumn, 0);
	private static Point2D currentOutcomeLoc = new Point2D.Double(outcomeColumn, 0);
	private static Point2D currentInterventionLoc = new Point2D.Double(interventionColumn1, 0);
	
	private static Point2D getNextDiagnosisLocation() {
		currentDiagnosisLoc = new Point2D.Double(diagnosisColumn, currentDiagnosisLoc.getY() + diagnosisSpacing);
		return currentDiagnosisLoc;
	}
	
	private static Point2D getNextOutcomeLocation() {
		currentOutcomeLoc = new Point2D.Double(outcomeColumn, currentOutcomeLoc.getY() + outcomeSpacing);
		return currentOutcomeLoc;
	}
	
	private static Point2D getNextInterventionLocation() {
		currentInterventionLoc = new Point2D.Double(interventionColumn1, currentInterventionLoc.getY() + interventionSpacing);
		return currentInterventionLoc;
	}

        public static Point2D[] getCurrentLocationInformation()
        {
            Point2D[] locInfo = {currentDiagnosisLoc, currentOutcomeLoc, currentInterventionLoc};
            return locInfo;
        }

        public static void setCurrentLocationInformation(Point2D[] locInfo)
        {
            currentDiagnosisLoc = locInfo[0];
            currentOutcomeLoc = locInfo[1];
            currentInterventionLoc = locInfo[2];
        }
	
	
	//***** DATA MEMBERS *****//
	private List<NNNObject> nnnObjects;
	private Boolean selected;
	private Point2D location;
	
	
	//***** CONSTRUCTORS *****//
	public GraphNode(NNNObject nnnObject)
	{
		nnnObjects = new ArrayList<NNNObject>();
		nnnObjects.add(nnnObject);
		selected = false;
		
		// Assign location
		if (((NNNObject)nnnObjects.get(0)) instanceof Diagnosis)
			location = getNextDiagnosisLocation();
		else if (((NNNObject)nnnObjects.get(0)) instanceof Outcome)
			location = getNextOutcomeLocation();
		else if (((NNNObject)nnnObjects.get(0)) instanceof Intervention)
			location = getNextInterventionLocation();
	}
	
	
	//***** METHODS *****//
	public String getName()
	{
		return ((NNNObject)nnnObjects.get(0)).getName();
	}
	
	public String getCode()
	{
		return ((NNNObject)nnnObjects.get(0)).getCode();
	}
	
	public String getDescription()
	{
		return ((NNNObject)nnnObjects.get(0)).getDefinition();
	}
	
	public NNNObject getNNNObject()
	{
		return nnnObjects.get(0);
	}
	
	public List<NNNObject> getNNNObjects()
	{
		return nnnObjects;
	}
	
	public NodeType getType()
	{
		if (((NNNObject)nnnObjects.get(0)) instanceof Diagnosis)
			return NodeType.DIAGNOSIS;
		else if (((NNNObject)nnnObjects.get(0)) instanceof Outcome)
			return NodeType.OUTCOME;
		else if (((NNNObject)nnnObjects.get(0)) instanceof Intervention)
			return NodeType.INTERVENTION;
		
		return NodeType.DIAGNOSIS; //We should never get here
	}
	
	public RenderMode getRenderMode()
	{
		if (selected)
			return RenderMode.SELECTED;
		else
			return RenderMode.NORMAL;
	}
	
	public Point2D getLocation()
	{
		return location;
	}
	
	public Boolean getSelected()
	{
		return selected;
	}
	
	public void setSelected(Boolean selected)
	{
		this.selected = selected;
	}
	
	public void addNNNObject(NNNObject nnnObject)
	{
		nnnObjects.add(nnnObject);
	}
	
	public void removeNNNObject(NNNObject nnnObject)
	{
		nnnObjects.remove(nnnObject);
	}
	
	public void mouseClicked(MouseEvent e)
	{
		if (e.getButton() == MouseEvent.BUTTON3)  // Right click
		{
                    //Show popup menu of linked nodes to add to graph
                    AppController.showPopupMenu(e, this);
		} 
		//else if (e.getButton() == MouseEvent.BUTTON1){  // Left click
			if (e.isShiftDown())
				AppController.addSelectedNode(this);
			else
				AppController.setSelectedNode(this);
		//}
	}

        public void mouseReleased(MouseEvent e)
	{
            if(AppController.getMouseMode() == ModalGraphMouse.Mode.PICKING)
                location = e.getPoint();
	}
}

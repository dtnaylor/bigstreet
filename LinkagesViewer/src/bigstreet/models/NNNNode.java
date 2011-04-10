//package bigstreet.models;
//
//import java.awt.Color;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//
//import edu.uci.ics.jung.graph.Graph;
//import edu.uci.ics.jung.visualization.VisualizationViewer;
//import bigstreet.controllers.AppController;
//
//public class NNNNode {// extends NNNGraphElement {
//	// Static fields for handling assigning diagnosis selection colors
//	private static int diagnosisNumber = -1;
//	//private static Color[] diagnosisColors = {Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK, Color.YELLOW, Color.LIGHT_GRAY, Color.BLUE, Color.GREEN, Color.RED};
//	private static Color[] diagnosisColors = {new Color(0.95f, 0.48f, 0.12f, 1.0f),
//											  new Color(0.00f, 0.43f, 0.41f, 1.0f),
//											  new Color(0.00f, 0.39f, 0.63f, 1.0f),
//											  new Color(0.00f, 0.54f, 0.59f, 1.0f),
//											  new Color(0.47f, 0.40f, 0.68f, 1.0f),
//											  new Color(0.97f, 0.59f, 0.11f, 1.0f)};
//	private static Color nextDiagnosisColor()
//	{
//		diagnosisNumber++;
//		return diagnosisColors[diagnosisNumber % diagnosisColors.length];
//	}
//	
//	//***** DATA MEMBERS *****//
//	private NNNObject nnnObject;
//	private boolean visible;
//	private boolean selected;
//	private List<NNNNode> selectedPredecessors;
//	private NNNNode mostRecentlySelectedPredecessor; // (when I was selected, that is)
//	private Color selectionColor;
//	
//	
//	//***** CONSTRUCTORS *****//
//	public NNNNode(NNNObject nnnObject)
//	{
//		this.nnnObject = nnnObject;
//		this.visible = false;
//		this.selected = false;
//		this.selectedPredecessors = new ArrayList<NNNNode>();
//		if(getType() == NodeType.DIAGNOSIS)
//			selectionColor = nextDiagnosisColor();
//		else
//			selectionColor = Color.BLACK;
//	}
//	
//	
//	//***** PROPERTIES *****//
//	public String getName()
//	{
//		return nnnObject.getName();
//	}
//	
//	public NNNObject getNNNObject() {
//		return nnnObject;
//	}
//	
//	public String getCode()
//	{
//		return nnnObject.getCode();
//	}
//	
//	public String getDescription()
//	{
//		return nnnObject.getDefinition();
//	}
//	
//	public boolean getVisible()
//	{
//		return this.visible;
//	}
//	
//	public NodeType getType()
//	{
//		if (nnnObject instanceof Diagnosis)
//			return NodeType.DIAGNOSIS;
//		else if (nnnObject instanceof Outcome)
//			return NodeType.OUTCOME;
//		else if (nnnObject instanceof Intervention)
//			return NodeType.INTERVENTION;
//		
//		return NodeType.DIAGNOSIS; //We should never get here
//	}
//	
//	
//	//***** METHODS *****//
//	public void mouseClicked(MouseEvent e)
//	{
//		// If the user unknowingly clicks on an invisible node, ignore it
//		if(getRenderMode() == RenderMode.INVISIBLE)
//			return;
//		
//		if (e.getButton() == MouseEvent.BUTTON3)
//		{
//			// Toggle whether or not this node is selected
//			visible = !visible;
//			
//			final VisualizationViewer<NNNNode,NNNEdge> vv =
//	            (VisualizationViewer<NNNNode,NNNEdge>)e.getSource();
//			
//			Graph<NNNNode, NNNEdge> g = vv.getGraphLayout().getGraph();
//			
//			if(visible)
//				makeVisible(g);
//			else
//				makeInvisible(g);
//		} 
//		else if (e.getButton() == MouseEvent.BUTTON1){
//			AppController.setCurrentNode(this);
//		}
//	}
//	
//	private void makeVisible(Graph<NNNNode, NNNEdge> g)
//	{
//		// Save which of my predecessor nodes was selected
//		// most recently (so we have an idea of what "selection
//		// path" led to my being selected).
//		if (selectedPredecessors.size() > 0)
//			mostRecentlySelectedPredecessor = selectedPredecessors.get(selectedPredecessors.size() - 1);
//		
//		// Set my selection color to my most recently selected
//		// predecessor's color (unless I'm a diagnosis, in which
//		// case a color was assigned to me when I was created.)
//		if (getType() != NodeType.DIAGNOSIS)
//			selectionColor = mostRecentlySelectedPredecessor.getSelectionColor();
//		
//		// Tell all immediate successors that I've been selected
//		for (NNNNode node : g.getSuccessors(this))
//		{
//			node.predecessorSelected(this, g);
//		}
//	}
//	
//	private void makeInvisible(Graph<NNNNode, NNNEdge> g)
//	{
//		// Set my selection color to black
//		if (getType() != NodeType.DIAGNOSIS)
//			selectionColor = Color.BLACK;
//		
//		// Tell all immediate successors that I've been deselected
//		for (NNNNode node : g.getSuccessors(this))
//		{
//			node.predecessorDeselected(this, g);
//		}
//	}
//	
//	public void select()
//	{
//		selected = true;
//	}
//	
//	public void deselect()
//	{
//		selected = false;
//	}
//	
//	protected void predecessorSelected(NNNNode node, Graph<NNNNode, NNNEdge> g)
//	{
//		// We want the most recently selected predecessor to be last in the list
//		// (for selection coloring), so if it's already in the list, remove it
//		// first and then we'll add it again
//		if(selectedPredecessors.contains(node)) selectedPredecessors.remove(node);
//		
//		selectedPredecessors.add(node);
//	}
//	
//	protected void predecessorDeselected(NNNNode node, Graph<NNNNode, NNNEdge> g)
//	{
//		if(selectedPredecessors.contains(node)) selectedPredecessors.remove(node);
//		
//		if(selectedPredecessors.isEmpty())
//		{
//			visible = false;
//			makeInvisible(g);
//		}
//	}
//	
//	public RenderMode getRenderMode()
//	{
//		if (selected)
//			return RenderMode.SELECTED;
//		else if(visible)
//			return RenderMode.VISIBLE;
//		else if(!selectedPredecessors.isEmpty() || getType() == NodeType.DIAGNOSIS)
//			return RenderMode.GHOSTED;
//		else
//			return RenderMode.INVISIBLE;
//	}
//	
//	public Color getSelectionColor()
//	{
//		return selectionColor;
////		if(this.type == NodeType.DIAGNOSIS)
////			return selectionColor;
////		else if(!selectedPredecessors.isEmpty())
////			return selectedPredecessors.get(selectedPredecessors.size() - 1).getSelectionColor();
////		else
////			return Color.BLACK;
//	}
//	
//	public NNNNode getMostRecentlySelectedPredecessor()
//	{
//		return mostRecentlySelectedPredecessor;
//	}
//	
//	public String toString()
//	{
//		return getName();
//	}
//}
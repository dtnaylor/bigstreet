package linkagesviewer.models;

//package bigstreet.models;
//
//import java.awt.Color;
//
//public class NNNEdge {// extends NNNGraphElement {
//	//***** DATA MEMBERS *****//
//	private EdgeType type;
//	private NNNNode startNode;
//	private NNNNode endNode;
//	
//	
//	//***** CONSTRUCTORS *****//
//	public NNNEdge(EdgeType type, NNNNode startNode, NNNNode endNode)
//	{
//		this.type = type;
//		this.startNode = startNode;
//		this.endNode = endNode;
//	}
//	
//	
//	//***** PROPERTIES *****//
//	public EdgeType getType()
//	{
//		return type;
//	}
//	
//	public RenderMode getRenderMode()
//	{
//		if(!startNode.getVisible() && startNode.getRenderMode() != RenderMode.INVISIBLE)
//			return RenderMode.GHOSTED;
//		
//		if(startNode.getVisible())
//		{
//			if(endNode.getVisible())
//				return RenderMode.VISIBLE;
//			else
//				return RenderMode.GHOSTED;
//		}
//
//		return RenderMode.INVISIBLE;
//	}
//	
//	public Color getSelectionColor()
//	{
//		if (startNode == endNode.getMostRecentlySelectedPredecessor())
//			return startNode.getSelectionColor();
//		else
//			return Color.BLACK;
//	}
//	
//	
//	//***** METHODS *****//
//	public String toString()
//	{
//		return this.type.toString();
//	}
//	
//	
//}

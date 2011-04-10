package edu.uiowa.nursing.models;

import java.awt.Color;

public class GraphEdge {
	//***** DATA MEMBERS *****//
	private EdgeType type;
	private RenderMode renderMode;
	private GraphNode startNode;
	private GraphNode endNode;
	
	
	//***** CONSTRUCTORS *****//
	public GraphEdge(EdgeType type, RenderMode renderMode, GraphNode startNode, GraphNode endNode)
	{
		this.type = type;
		this.renderMode = renderMode;
		this.startNode = startNode;
		this.endNode = endNode;
	}
	
	
	//***** PROPERTIES *****//
	public EdgeType getType()
	{
		return type;
	}
	
	public RenderMode getRenderMode()
	{
		return renderMode;
	}
	
	
	//***** METHODS *****//
	public String toString()
	{
		return this.type.toString();
	}
	
}

package edu.uiowa.nursing.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.font.TextAttribute;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uiowa.nursing.controllers.AppController;
import edu.uiowa.nursing.views.NNNPickingGraphMousePlugin;
import edu.uiowa.nursing.views.NNNVertexLabelRenderer;

public class NNNGraph {
	//***** DATA MEMBERS *****//
	private Graph<NNNNode, NNNEdge> g;
	private List<Diagnosis> displayedDiagnoses;
	private List<Diagnosis> correlatedDiagnoses;
	
	VisualizationViewer<NNNNode,NNNEdge> vv;
	DefaultModalGraphMouse graphMouse;
	ViewScalingControl zoomControl;

	//***** CONSTRUCTORS *****//
	public NNNGraph(List<Diagnosis> diagnoses)
	{
		// Graph<V, E> where V is the type of the vertices 
		// and E is the type of the edges 
		g = new DirectedSparseMultigraph<NNNNode, NNNEdge>();
		displayedDiagnoses = new ArrayList<Diagnosis>();
		correlatedDiagnoses = new ArrayList<Diagnosis>();

		addDiagnoses(diagnoses);		
	}
	
	public NNNGraph(Diagnosis diagnosis)
	{
		this(Arrays.asList(diagnosis));
	}

	//***** METHODS *****//
	
	// PUBLIC
	public VisualizationViewer getView()
	{
		// The Layout<V, E> is parameterized by the vertex and edge types 
		Layout<NNNNode, NNNEdge> layout = new DAGLayout(g);
		((SpringLayout) layout).setForceMultiplier(10);
		((SpringLayout) layout).setRepulsionRange(15);
		layout.setSize(new Dimension(AppController.GRAPH_SIZE)); // sets the initial size of the space
		
		
		// The BasicVisualizationServer<V,E> is parameterized by the edge types 
		vv = new VisualizationViewer<NNNNode,NNNEdge>(layout); 
		vv.setPreferredSize(new Dimension(AppController.WINDOW_SIZE)); //Sets the viewing area size
		
		// Rotate so diagnoses are on top
		Dimension d = layout.getSize();
    	Point2D center = new Point2D.Double(d.width/2, d.height/2);
    	vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).rotate(Math.PI / 2.0, center);
		
		
		
		// Setup up a new vertex to paint transformer... 
		Transformer<NNNNode,Paint> vertexPaintTransformer = new Transformer<NNNNode,Paint>() {
			public Paint transform(NNNNode n) { 
				Color color;
				
				if(n.getRenderMode() == RenderMode.SELECTED)
				{
					switch(n.getType())
					{
						case DIAGNOSIS:
							color = new Color(0.0f, 0.0f, 0.5f, 0.8f);
							break;
						case OUTCOME:
							color = new Color(0.0f, 0.5f, 0.0f, 0.8f);
							break;
						case INTERVENTION:
							color = new Color(0.5f, 0.0f, 0.0f, 0.8f);
							break;
						default:
							color = Color.BLACK;
							break;
					}
				}
				else if(n.getRenderMode() == RenderMode.GHOSTED)
				{
					color = new Color(0.0f, 0.0f, 0.0f, 0.1f);
				}
				else if (n.getRenderMode() == RenderMode.INVISIBLE)
				{
					color = new Color(0.0f, 0.0f, 0.0f, 0.0f);
				}
				else
				{
					color = Color.BLACK;
				}
				
				
				return color;
			}
		};
		
		// Set up a new stroke Transformer for the edges 
		final Stroke defaultEdgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f); 
		final Stroke outcomeEdgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f); 
		final Stroke majorInterventionEdgeStroke = new BasicStroke(3.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f); 
		final Stroke suggestedInterventionEdgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f);
		final float optionalInterventionDash[] = {10.0f};
		final Stroke optionalInterventionEdgeStroke = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, optionalInterventionDash, 0.0f);
		
		
		Transformer<NNNEdge, Stroke> edgeStrokeTransformer = 
			new Transformer<NNNEdge, Stroke>() { 
				public Stroke transform(NNNEdge e) {
					switch(e.getType())
					{
						case OUTCOME:
							return outcomeEdgeStroke;
						case MAJOR_INTERVENTION:
							return majorInterventionEdgeStroke;
						case SUGGESTED_INTERVENTION:
							return suggestedInterventionEdgeStroke;
						case OPTIONAL_INTERVENTION:
							return optionalInterventionEdgeStroke;
						default:
							return defaultEdgeStroke;
					}
				}
			};
			
		Transformer<NNNEdge, Paint> edgeDrawPaintTransformer = 
			new Transformer<NNNEdge, Paint>() { 
				public Paint transform(NNNEdge e) {
					Color color;
					
					switch(e.getRenderMode())
					{
						case SELECTED:
							color = e.getSelectionColor();
							break;
						case CORRELATED:
							color = Color.BLACK; //TODO: change me
							break;
						case GHOSTED:
							color = new Color(0.0f, 0.0f, 0.0f, 0.05f);
							break;
						case INVISIBLE:
							color = new Color(0.0f, 0.0f, 0.0f, 0.0f);
							break;
						default:
							color = Color.BLACK;
							break;
					}
					
					return color;
				}
			};
			
		Transformer<NNNNode, Paint> vertexDrawPaintTransformer = 
			new Transformer<NNNNode, Paint>() { 
				public Paint transform(NNNNode n) {
					Color color;
					
					switch(n.getRenderMode())
					{
						case SELECTED:
							color = n.getSelectionColor();
							break;
						case CORRELATED:
							color = Color.BLACK; //TODO: change me
							break;
						case GHOSTED:
							color = new Color(0.0f, 0.0f, 0.0f, 0.05f);
							break;
						case INVISIBLE:
							color = new Color(0.0f, 0.0f, 0.0f, 0.0f);
							break;
						default:
							color = Color.BLACK;
							break;
					}
					
					return color;
				}
			};
			
		Transformer<NNNNode, String> nodeLabelTransformer = 
			new Transformer<NNNNode, String>() { 
				public String transform(NNNNode n) {
					String label;
					
					switch(n.getType())
					{
						case DIAGNOSIS:
							label = "DIAGNOSIS<>" + n.getName();
							break;
						case OUTCOME:
							label = "OUTCOME<>" + n.getName();
							break;
						case INTERVENTION:
							label = "INTERVENTION<>" + n.getName();
							break;
						default:
							label = n.toString();
							break;
					}
					
					return label;
				}
			};
			
		Transformer<NNNNode, Font> nodeFontTransformer = 
			new Transformer<NNNNode, Font>() { 
				public Font transform(NNNNode n) {
					HashMap<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
					
					switch(n.getRenderMode())
					{
						case SELECTED:
							attributes.put(TextAttribute.FOREGROUND, new Color(0.0f, 0.0f, 0.0f, 1.0f));
							break;
						case CORRELATED:
							attributes.put(TextAttribute.FOREGROUND, new Color(0.0f, 0.0f, 0.0f, 1.0f)); //TODO: change me
							break;
						case GHOSTED:
							attributes.put(TextAttribute.FOREGROUND, new Color(0.0f, 0.0f, 0.0f, 0.15f));
							break;
						case INVISIBLE:
							attributes.put(TextAttribute.FOREGROUND, new Color(0.0f, 0.0f, 0.0f, 0.0f));
							break;
						default:
							attributes.put(TextAttribute.FOREGROUND, new Color(0.0f, 0.0f, 0.0f, 1.0f));
							break;
					}
					
					return new Font(attributes);
				}
			};

		class NNNVertexPredicate<V,E> 
	    	implements Predicate<Context<Graph<NNNNode, NNNEdge>, NNNNode>> {

	        public boolean evaluate(Context<Graph<NNNNode,NNNEdge>,NNNNode> c) {
	            return ((NNNNode)c.element).getRenderMode() != RenderMode.INVISIBLE;
	        }
	        
	    }
		
		vv.getRenderContext().setVertexFillPaintTransformer(vertexPaintTransformer);
		vv.getRenderContext().setVertexDrawPaintTransformer(vertexDrawPaintTransformer);
		vv.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vv.getRenderContext().setEdgeDrawPaintTransformer(edgeDrawPaintTransformer);
		vv.getRenderContext().setVertexLabelTransformer(nodeLabelTransformer);
		vv.getRenderContext().setArrowDrawPaintTransformer(edgeDrawPaintTransformer);
		vv.getRenderContext().setArrowFillPaintTransformer(edgeDrawPaintTransformer);
		vv.getRenderContext().setVertexFontTransformer(nodeFontTransformer);
		vv.getRenderContext().setVertexIncludePredicate(new NNNVertexPredicate<NNNNode, NNNEdge>());
		vv.getRenderer().setVertexLabelRenderer(new NNNVertexLabelRenderer<NNNNode, NNNEdge>());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.AUTO);
		
		
		
		//Default mouse and keyboard interactivity
		//DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
		graphMouse = new DefaultModalGraphMouse();
		graphMouse.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
		graphMouse.add(new NNNPickingGraphMousePlugin());
		vv.setGraphMouse(graphMouse);
		vv.addKeyListener(graphMouse.getModeKeyListener());
		
		zoomControl = new ViewScalingControl();
		
		return vv;
	}
	
	public void addDiagnoses(List<Diagnosis> diagnosesToAdd)
	{		
		for (Diagnosis d : diagnosesToAdd)
		{
			if (displayedDiagnoses.contains(d))
				return;
			else
				displayedDiagnoses.add(d); // Add to diagnoses so it's not removed if we stop showing correlated diagnoses			
			if (correlatedDiagnoses.contains(d)) return;  // we don't need to redraw the graph
			addDiagnosis(d);
		}
		
		AppController.graphUpdated();
	}

	public void updateCorrelatedNodes(int numToShow)
	{
		correlatedDiagnoses.clear();  //TODO: Remove these from graph!!!
		for (Diagnosis d : displayedDiagnoses){
			for (int i = 0; i < Math.min(numToShow, d.getCorrelatedDiagnoses().size()); i++)
			{
				Diagnosis correlated = d.getCorrelatedDiagnoses().get(i);
				if (!correlatedDiagnoses.contains(correlated))
				{
					correlatedDiagnoses.add(correlated);
					addDiagnosis(correlated);
				}
			}
		}
		
		AppController.graphUpdated();
	}
	
	public void setMouseMode(ModalGraphMouse.Mode mode)
	{
		graphMouse.setMode(mode);
	}
	
	public void zoomIn()
	{
		zoomControl.scale(vv, 1.1f, new Point(0,0));
	}
	
	public void zoomOut()
	{
		zoomControl.scale(vv, 0.9f, new Point(0,0));
	}
	
	public void saveScreenShot(String path)
	{
//		Dimension size = myGraph.getSize();
//		BufferedImage myImage =
//		    new BufferedImage(size.width, size.height,
//		        BufferedImage.TYPE_INT_RGB);
//		Graphics2D g2 = myImage.createGraphics();
//		myGraph.paintComponent(g2);
//		try {
//			OutputStream out = new FileOutputStream(filename);
//		    JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//		    encoder.encode(myImage);
//		    out.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
		
		
		Dimension size = AppController.GRAPH_SIZE;

	    BufferedImage bi = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_BGR);
	    Graphics2D graphics = bi.createGraphics();
	    graphics.setColor(Color.WHITE);
	    graphics.fillRect(0,0, size.width, size.height);
	    
	    Rectangle oldBounds = vv.getBounds();
	    vv.setBounds(0, 0, size.width, size.height);
	    vv.paint(graphics);
	    vv.setBounds(oldBounds);

	    try{
	       ImageIO.write(bi,"jpeg",new File(path));
	    }catch(Exception e){e.printStackTrace();}
	}
	
	// PRIVATE
	private void addDiagnosis(Diagnosis diagnosis)
	{
		// Get a list of names of nodes already in the graph
		Hashtable existingNodes = new Hashtable<String, NNNNode>();
		for (NNNNode n : g.getVertices())
		{
			existingNodes.put(n.getName(), n);
		}
		
		if (existingNodes.containsKey(diagnosis.getName())) return;
		
		//Add diagnosis node
		NNNNode diagnosisNode = new NNNNode(diagnosis.getName(), diagnosis.getCode(), diagnosis.getDefinition(), NodeType.DIAGNOSIS);
		g.addVertex(diagnosisNode);
		existingNodes.put(diagnosisNode.getName(), diagnosisNode);
		
		//Add outcomes
		for (Outcome o : diagnosis.getOutcomes())
		{
			// Get a NNNNode representing Outcome o
			NNNNode outcomeNode;
			if (existingNodes.containsKey(o.getName()))
			{
				outcomeNode = (NNNNode) existingNodes.get(o.getName());
			}
			else
			{
				outcomeNode = new NNNNode(o.getName(), o.getCode(), o.getDefinition(), NodeType.OUTCOME);
				existingNodes.put(o.getName(), outcomeNode);
			}
			
			// Add outcomeNode as a vertex in g
			if(!g.containsVertex(outcomeNode))
				g.addVertex(outcomeNode);
			
			// Add an edge between diagnosisNode and outcomeNode
			g.addEdge(new NNNEdge(EdgeType.OUTCOME, diagnosisNode, outcomeNode), diagnosisNode, outcomeNode);
			
			
			//Add interventions
			for (EdgeType t : EdgeType.values())
			{
				if(t == EdgeType.OUTCOME) continue; //We only want interventions
				
				for (Intervention h : o.getInterventions(t))
				{
					//Get a NNNNode representing Intervention h
					NNNNode interventionNode;
					if(existingNodes.containsKey(h.getName()))
					{
						interventionNode = (NNNNode) existingNodes.get(h.getName());
					}
					else
					{
						interventionNode = new NNNNode(h.getName(), h.getCode(), "", NodeType.INTERVENTION);
						existingNodes.put(h.getName(), interventionNode);
					}
					
					// Add interventionNode as a vertex in g
					if(!g.getVertices().contains(interventionNode))
						g.addVertex(interventionNode);
					
					if(!g.getSuccessors(outcomeNode).contains(interventionNode))
					{
						// Add an edge between outcomeNode and interventionNode
						g.addEdge(new NNNEdge(t, outcomeNode, interventionNode), outcomeNode, interventionNode);
					}
				}
			}
		}
	}
}

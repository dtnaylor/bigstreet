package edu.uiowa.nursing.views;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uiowa.nursing.models.NNNEdge;
import edu.uiowa.nursing.models.NNNNode;
import edu.uiowa.nursing.models.RenderMode;

public class NNNPickingGraphMousePlugin extends PickingGraphMousePlugin<NNNNode, NNNEdge> {
	public void mouseClicked(MouseEvent e)
	{
		super.mouseClicked(e);
		
		final VisualizationViewer<NNNNode,NNNEdge> vv =
            (VisualizationViewer<NNNNode,NNNEdge>)e.getSource();
    
		Point2D p = e.getPoint();
    
	    GraphElementAccessor<NNNNode,NNNEdge> pickSupport = vv.getPickSupport();
	    if(pickSupport != null) {
	        final NNNNode v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
	        if(v != null) {
	        	v.mouseClicked(e);
	        } else {
	            final NNNEdge edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
	            if(edge != null) {
	            	//edge.setRenderMode(RenderMode.SELECTED);
	            }
	        }
	    }
	}
}

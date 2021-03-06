package linkagesviewer.views;

import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import linkagesviewer.models.GraphEdge;
import linkagesviewer.models.GraphNode;
import linkagesviewer.models.RenderMode;

public class NNNPickingGraphMousePlugin extends PickingGraphMousePlugin<GraphNode, GraphEdge> {
	public void mouseClicked(MouseEvent e)
	{
		super.mouseClicked(e);
		
		final VisualizationViewer<GraphNode,GraphEdge> vv =
            (VisualizationViewer<GraphNode,GraphEdge>)e.getSource();
    
		Point2D p = e.getPoint();
    
	    GraphElementAccessor<GraphNode,GraphEdge> pickSupport = vv.getPickSupport();
	    if(pickSupport != null) {
	        final GraphNode v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
	        if(v != null) {
	        	v.mouseClicked(e);
	        } else {
	            final GraphEdge edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
	            if(edge != null) {
	            	//edge.setRenderMode(RenderMode.SELECTED);
	            }
	        }
	    }
	}

        public void mouseReleased(MouseEvent e)
	{
		super.mouseReleased(e);

		final VisualizationViewer<GraphNode,GraphEdge> vv =
            (VisualizationViewer<GraphNode,GraphEdge>)e.getSource();

		Point2D p = e.getPoint();

	    GraphElementAccessor<GraphNode,GraphEdge> pickSupport = vv.getPickSupport();
	    if(pickSupport != null) {
	        final GraphNode v = pickSupport.getVertex(vv.getGraphLayout(), p.getX(), p.getY());
	        if(v != null) {
	        	v.mouseReleased(e);
	        } else {
	            final GraphEdge edge = pickSupport.getEdge(vv.getGraphLayout(), p.getX(), p.getY());
	            if(edge != null) {
	            	//edge.setRenderMode(RenderMode.SELECTED);
	            }
	        }
	    }
	}
}

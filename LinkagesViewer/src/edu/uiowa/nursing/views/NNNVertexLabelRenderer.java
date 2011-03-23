package edu.uiowa.nursing.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Context;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.layout.PersistentLayout.Point;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.transform.BidirectionalTransformer;
import edu.uci.ics.jung.visualization.transform.shape.GraphicsDecorator;
import edu.uci.ics.jung.visualization.transform.shape.ShapeTransformer;
import edu.uci.ics.jung.visualization.transform.shape.TransformingGraphics;

public class NNNVertexLabelRenderer<V,E> extends BasicVertexLabelRenderer<V, E> {
	/**
	 * Labels the specified vertex with the specified label.  
	 * Uses the font specified by this instance's 
	 * <code>VertexFontFunction</code>.  (If the font is unspecified, the existing
	 * font for the graphics context is used.)  If vertex label centering
	 * is active, the label is centered on the position of the vertex; otherwise
     * the label is offset slightly.
     */
    public void labelVertex(RenderContext<V,E> rc, Layout<V,E> layout, V v, String label) {
    	
    	// Split the value of label around the '|' character to find
    	// out if we're labeling a diagnosis, an outcome, or an intervention
    	// so we can choose the appropriate positioner
    	String labelText = label;
    	String[] labelArray = label.split("<>");
    	
    	if(labelArray.length == 2)
    	{
    		pickPositioner(labelArray[0]);
    		labelText = labelArray[1];
    	}
    	
    	
    	
    	Graph<V,E> graph = layout.getGraph();
        if (rc.getVertexIncludePredicate().evaluate(Context.<Graph<V,E>,V>getInstance(graph,v)) == false) {
        	return;
        }
        Point2D pt = layout.transform(v);
        pt = rc.getMultiLayerTransformer().transform(Layer.LAYOUT, pt);

        float x = (float) pt.getX();
        float y = (float) pt.getY();

        Component component = prepareRenderer(rc, rc.getVertexLabelRenderer(), labelText,
        		rc.getPickedVertexState().isPicked(v), v);
        GraphicsDecorator g = rc.getGraphicsContext();
        Dimension d = component.getPreferredSize();
        AffineTransform xform = AffineTransform.getTranslateInstance(x, y);
        
    	Shape shape = rc.getVertexShapeTransformer().transform(v);
    	shape = xform.createTransformedShape(shape);
    	if(rc.getGraphicsContext() instanceof TransformingGraphics) {
    		BidirectionalTransformer transformer = ((TransformingGraphics)rc.getGraphicsContext()).getTransformer();
    		if(transformer instanceof ShapeTransformer) {
    			ShapeTransformer shapeTransformer = (ShapeTransformer)transformer;
    			shape = shapeTransformer.transform(shape);
    		}
    	}
    	Rectangle2D bounds = shape.getBounds2D();

    	java.awt.Point p = null;
    	if(position == Position.AUTO) {
    		Dimension vvd = rc.getScreenDevice().getSize();
    		if(vvd.width == 0 || vvd.height == 0) {
    			vvd = rc.getScreenDevice().getPreferredSize();
    		}
    		p = getAnchorPoint(bounds, d, getPositioner().getPosition(x, y, vvd));
    	} else {
    		p = getAnchorPoint(bounds, d, position);
    	}
        g.draw(component, rc.getRendererPane(), p.x, p.y, d.width, d.height, true);
    }
    
    private void pickPositioner(String nodeType)
    {
    	if(nodeType.equals("DIAGNOSIS")) setPositioner(new DiagnosisPositioner());
    	else if(nodeType.equals("OUTCOME")) setPositioner(new OutcomePositioner());
    	else if(nodeType.equals("INTERVENTION")) setPositioner(new InterventionPositioner());
    	else setPositioner(new OutsidePositioner());
    }
    
    public static class DiagnosisPositioner implements Positioner {
    	public Position getPosition(float x, float y, Dimension d) {
    		return Position.W;
    	}
    }
    
    public static class OutcomePositioner implements Positioner {
    	public Position getPosition(float x, float y, Dimension d) {
    		int cx = d.width/2;
    		int cy = d.height/2;
    		if(x > cx && y > cy) return Position.SW;
    		if(x > cx && y < cy) return Position.NW;
    		if(x < cx && y > cy) return Position.SW;
    		return Position.NW;
    	}
    }
    
    public static class InterventionPositioner implements Positioner {
    	public Position getPosition(float x, float y, Dimension d) {
    		return Position.E;
    	}
    }
}

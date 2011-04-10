//package edu.uiowa.nursing.models;
//
//import java.util.Hashtable;
//
//public class NNNGraphElement {
//	//***** DATA MEMBERS *****//
//	private Hashtable<RenderMode, Integer> renderModeCounts;
//	
//	//***** CONSTRUCTOR *****//
//	public NNNGraphElement(boolean visible)
//	{
//		renderModeCounts = new Hashtable<RenderMode, Integer>();
//		for(RenderMode m : RenderMode.values())
//		{
//			renderModeCounts.put(m, 0);
//		}
//		
//		if(visible)
//		{
//			renderModeCounts.put(RenderMode.GHOSTED, 1);
//		}
//	}
//	
//	//***** METHODS *****//
//	public RenderMode getRenderMode() 
//	{
//		if(renderModeCounts.get(RenderMode.VISIBLE) > 0)
//			return RenderMode.VISIBLE;
//		else if(renderModeCounts.get(RenderMode.GHOSTED) > 0)
//			return RenderMode.GHOSTED;
//		else if(renderModeCounts.get(RenderMode.CORRELATED) > 0)
//			return RenderMode.CORRELATED;
//		else
//			return RenderMode.INVISIBLE;
//	}
//	
//	public boolean getSelected()
//	{
//		return renderModeCounts.get(RenderMode.VISIBLE) > 0;
//	}
//	
//	public void incrementSelected()
//	{
//		renderModeCounts.put(RenderMode.VISIBLE, renderModeCounts.get(RenderMode.VISIBLE) + 1);
//	}
//	
//	public void decrementSelected()
//	{
//		renderModeCounts.put(RenderMode.VISIBLE, renderModeCounts.get(RenderMode.VISIBLE) - 1);
//	}
//	
//	public void incrementConnected()
//	{
//		renderModeCounts.put(RenderMode.GHOSTED, renderModeCounts.get(RenderMode.GHOSTED) + 1);
//	}
//	
//	public void decrementConnected()
//	{
//		renderModeCounts.put(RenderMode.GHOSTED, renderModeCounts.get(RenderMode.GHOSTED) - 1);
//	}
//}

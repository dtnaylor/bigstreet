package linkagesviewer.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.uci.ics.jung.algorithms.layout.DAGLayout;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
//import bigstreet.Utilities;
import linkagesviewer.controllers.AppController;
import linkagesviewer.models.Diagnosis;
import linkagesviewer.models.GraphNode;
import linkagesviewer.models.NNNGraph;
import linkagesviewer.models.NodeType;

public class MainWindow extends JFrame {
	// GUI elements
	
	// ===== TOOLBOX ===== //
	private JPanel panelToolbar;
	
	// Search panel
	private JPanel searchPanel;
	private JPanel panelSearchTextBox;
	private JTextField tbxSearch;
	private JButton btnSearch;
	
	private JList listResults;
	private JScrollPane listResultsScrollPane;
	
	// Display buttons panel
	private JPanel displayButtonsPanel;
	private JButton btnAdd;
	
	// Correlated nodes
	private JLabel lblCorrelated;
	private JSlider tbarCorrelated;
	
	// Node info panel
	private JLabel lblNodeInfo;
	private JPanel panelNodeInfo;
	
	// Bottom tools
	private JPanel panelBottomTools;
	
	// Controls
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	private JButton btnTranslation;
	private JButton btnSelection;
	private JButton btnRemove;
	private JButton btnClear;
	private JButton btnScreenShot;
	private JPanel panelControls;
	
	private JPanel panelTools;
	
	private VisualizationViewer graphView;
	
	
	// Constructor
	public MainWindow() 
	{
		super("NNN Linkages Browser");
		
		// Search
		tbxSearch = new JTextField(15);
		btnSearch = new JButton("Search");;
		panelSearchTextBox = new JPanel();
		panelSearchTextBox.setLayout(new BoxLayout(panelSearchTextBox, BoxLayout.LINE_AXIS));
		panelSearchTextBox.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		panelSearchTextBox.add(tbxSearch);
		panelSearchTextBox.add(btnSearch);
		
		listResults = new JList((ListModel) AppController.searchResults);
		listResultsScrollPane = new JScrollPane(listResults);
		btnAdd = new JButton("Add");
		
		displayButtonsPanel = new JPanel();
		displayButtonsPanel.setLayout(new BoxLayout(displayButtonsPanel, BoxLayout.LINE_AXIS));
		displayButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		displayButtonsPanel.add(Box.createHorizontalGlue());
		displayButtonsPanel.add(btnAdd);
		
		searchPanel = new JPanel(new BorderLayout());
		searchPanel.setBorder(BorderFactory.createTitledBorder("Search Diagnoses"));
		searchPanel.add(panelSearchTextBox, BorderLayout.NORTH);
		searchPanel.add(listResultsScrollPane, BorderLayout.CENTER);
		searchPanel.add(displayButtonsPanel, BorderLayout.SOUTH);
		
		// Correlations
		tbarCorrelated = new JSlider(JSlider.HORIZONTAL, 0, 0, 0);
		tbarCorrelated.setPaintTicks(true);
		tbarCorrelated.setMajorTickSpacing(1);
		tbarCorrelated.setSnapToTicks(true);
		tbarCorrelated.setVisible(false);
		lblCorrelated = new JLabel("Select a node to find others.");
		lblCorrelated.setVisible(false);
		lblCorrelated.setAlignmentX(RIGHT_ALIGNMENT);
		
		// Node info
		lblNodeInfo = new JLabel("Nothing selected.");
		panelNodeInfo = new JPanel(new BorderLayout());
		//panelNodeInfo.setLayout(new BoxLayout(panelNodeInfo, BoxLayout.LINE_AXIS));
		panelNodeInfo.setMinimumSize(new Dimension(searchPanel.getWidth(), 175));
		panelNodeInfo.setBorder(BorderFactory.createTitledBorder("Node Info"));
		panelNodeInfo.add(lblNodeInfo, BorderLayout.WEST);
		
		// Bottom tools
		
		ImageIcon zoomInIcon = createImageIcon("images/zoom_in.png");
		ImageIcon zoomOutIcon = createImageIcon("images/zoom_out.png");
		ImageIcon translationIcon = createImageIcon("images/arrows.png");
		ImageIcon selectionIcon = createImageIcon("images/pointer.png");
		ImageIcon removeIcon = createImageIcon("images/remove.png");
		ImageIcon clearIcon = createImageIcon("images/eraser.png");
		ImageIcon cameraIcon = createImageIcon("images/camera.png");
		btnZoomIn = new JButton(zoomInIcon);
		btnZoomOut = new JButton(zoomOutIcon);
		btnTranslation = new JButton(translationIcon);
		btnSelection = new JButton(selectionIcon);
		btnRemove = new JButton(removeIcon);
		btnClear = new JButton(clearIcon);
		btnScreenShot = new JButton(cameraIcon);
		btnZoomIn.setToolTipText("Zoom In");
		btnZoomOut.setToolTipText("Zoom Out");
		btnTranslation.setToolTipText("Translation Mode");
		btnSelection.setToolTipText("Selection Mode");
		btnRemove.setToolTipText("Remove Diagnosis");
		btnClear.setToolTipText("Clear Screen");
		btnScreenShot.setToolTipText("Save Screen Shot");
		panelControls = new JPanel();
		panelControls.setLayout(new FlowLayout());
		panelControls.add(btnZoomIn);
		panelControls.add(btnZoomOut);
		panelControls.add(btnTranslation);
		panelControls.add(btnSelection);
		panelControls.add(btnRemove);
		panelControls.add(btnClear);
		panelControls.add(btnScreenShot);
		
		
		// Tools
		panelToolbar = new JPanel(new BorderLayout());
		panelToolbar.setBorder(BorderFactory.createEtchedBorder());
		panelToolbar.setLayout(new BoxLayout(panelToolbar, BoxLayout.Y_AXIS));
		panelToolbar.add(searchPanel);
		panelToolbar.add(lblCorrelated);
		panelToolbar.add(tbarCorrelated);
		panelToolbar.add(panelNodeInfo);
		panelToolbar.add(panelControls);
		
		// Attach action listeners
		tbxSearch.addActionListener(new search());
		btnSearch.addActionListener(new search());
		
		btnAdd.addActionListener(new btnAdd_click());
		
		tbarCorrelated.addChangeListener(new tbarCorrelated_stateChanged());
		
		btnZoomIn.addActionListener(new btnZoomIn_click());
		btnZoomOut.addActionListener(new btnZoomOut_click());
		btnTranslation.addActionListener(new btnTranslation_click());
		btnSelection.addActionListener(new btnSelection_click());
		btnRemove.addActionListener(new btnRemove_click());
		btnClear.addActionListener(new btnClear_click());
		btnScreenShot.addActionListener(new btnScreenShot_click());
		
		
		
		this.getContentPane().add(panelToolbar, BorderLayout.WEST);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setSize(AppController.WINDOW_SIZE);
	}
	
	
	//***** METHODS *****//
	public void displayNodeInfo(GraphNode node)
	{
		String infoString = "<strong>" + node.getName().toUpperCase() 
			+ "</strong>  (" + node.getCode() + "): <br>" + node.getDescription();
		int curnum = 0;//node.getNNNObject().getNumCorrelatedNodesToShow();
		tbarCorrelated.setValue(curnum);
		switch (node.getType()) {
		case DIAGNOSIS:
			lblCorrelated.setVisible(true);
			tbarCorrelated.setVisible(true);
			//int num = node.getNNNObject().getNumberOfCorrelatedNodes(); 
			//lblCorrelated.setText("Show correlated diagnoses: "+curnum+"/"+num);
			//tbarCorrelated.setMaximum(num);
			break;
		case OUTCOME:
			lblCorrelated.setVisible(false);
			tbarCorrelated.setVisible(false);
			//lblCorrelated.setText("Show correlated outcomes: ");
			break;
		case INTERVENTION:
			lblCorrelated.setVisible(false);
			tbarCorrelated.setVisible(false);
			//lblCorrelated.setText("Show correlated interventions: ");
			break;
		
		}
		displayNodeInfo(infoString);
	}
	
	public void displayNodeInfo(String text)
	{
		//Utilities.wrapLabelText(lblNodeInfo, text);
	}
	
	public void clear()
	{
		// TODO: better way to do this?
		AppController.setDiagnosesToDisplay(new ArrayList<Diagnosis>());
		update();
	}
	
	public void update()
	{
		// Remove last graph from window
		if(graphView != null)
			MainWindow.this.getContentPane().remove(graphView);
		
		// Add new graph to window
		if(AppController.getGraphToDisplay() != null)
		{
			graphView = AppController.getGraphToDisplay().getView();
			MainWindow.this.getContentPane().add(graphView, BorderLayout.CENTER);
		}
		
		
		// Redraw window so graph shows up
		MainWindow.this.validate();
		
		System.out.println("here");
	}
	
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = MainWindow.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	
	//***** EVENT HANDLERS *****//
	private class search implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.search(tbxSearch.getText());
			tbxSearch.selectAll();
		}
		
	}
	
	
	private class btnAdd_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			try {
				
				for (int i : listResults.getSelectedIndices())
				{
					AppController.addDiagnosis(i);
				}				
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.toString(), "Error Displaying Graph", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private class btnZoomIn_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.zoomIn();
		}
	}
	
	private class btnZoomOut_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.zoomOut();
		}
	}
	
	private class btnTranslation_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.setMouseMode(ModalGraphMouse.Mode.TRANSFORMING);
		}
	}
	
	private class btnSelection_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.setMouseMode(ModalGraphMouse.Mode.PICKING);
		}
	}
	
	private class btnRemove_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}
	
	private class btnClear_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			clear();
		}
	}
	
	private class btnScreenShot_click implements ActionListener
	{

		public void actionPerformed(ActionEvent e) {
			AppController.saveScreenShot("image.jpg");
		}
	}
	
	private class tbarCorrelated_stateChanged implements ChangeListener
	{
		public void stateChanged(ChangeEvent e) {
		    JSlider source = (JSlider)e.getSource();
		    if (!source.getValueIsAdjusting()) {
		    	//AppController.setNumCorrelatedNodesToShow(source.getValue());
//			    switch (AppController.getSelectedNodes().getType()) {
//					case DIAGNOSIS:
//						int num = 0;//AppController.getCurrentNode().getNNNObject().getNumberOfCorrelatedNodes(); 
//						lblCorrelated.setText("Show correlated diagnoses: " + tbarCorrelated.getValue() +"/"+num);
//						break;
//					case OUTCOME:
//						lblCorrelated.setText("Show correlated outcomes: ");
//						break;
//					case INTERVENTION:
//						lblCorrelated.setText("Show correlated interventions: ");
//						break;
		    	
//			    }
			}
		}
	}
}

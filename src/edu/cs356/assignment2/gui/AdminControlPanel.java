package edu.cs356.assignment2.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.cs356.assignment2.gui.ControlPanel.ControlPanel;
import edu.cs356.assignment2.gui.TreeView.ACPTreeView;

@SuppressWarnings("serial")
public class AdminControlPanel extends JFrame {
	//Static Members
	private static AdminControlPanel instance = null;	/**Instance of this class*/
	public static final int HEIGHT = 480;	/**Preferred height for window*/
	public static final int WIDTH = 600;	/**Preferred width for window*/
	private static final int GAP = 5;		/**Gap between tree view and buttons/TextAreas*/
	
	//Swing Members
	private JPanel mainPanel = null;
	private ACPTreeView treeView = null;
	private ControlPanel control = null;
	//=========================================================
	// Constructor
	//=========================================================
	private AdminControlPanel() {
		super("Twitter Admin Control Panel");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Since this class is a singleton, this provides a way to get
	 * the instance. This method does lazy instantiation.
	 * @return	Instance of this class.
	 */
	public static AdminControlPanel getInstance() {
		if (instance == null)
			instance = new AdminControlPanel();
		return instance;
	}
	
	/**
	 * Initializes the main panel. This panel handles the size of 
	 * the main window. This method also initializes the TreeView,
	 * buttons, and TextAreas.
	 */
	private void initializePanel() {
		mainPanel = new JPanel();
		
		//Set default size, background color, and layout
		mainPanel.setBackground(Color.GRAY);
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainPanel.setLayout(new BorderLayout(GAP, GAP));	//Border layout has NORTH, SOUTH, EAST, WEST, CENTER areas.
		
		//Create the tree and control panel and add them to the panel.
		treeView = ACPTreeView.getInstance();
		mainPanel.add(treeView,BorderLayout.WEST);
		control = ControlPanel.getInstance();
		mainPanel.add(control, BorderLayout.EAST);
		
		
		//Add panel to ACP
		getContentPane().add(mainPanel);
		pack();		//Resizes to the preferred size
	}
	
	/**
	 * Initializes all of the swing objects and displays the main ACP window.
	 */
	public void startACP() {
		initializePanel();
		
		//Initialization complete so display the window.
		setVisible(true);
	}
}

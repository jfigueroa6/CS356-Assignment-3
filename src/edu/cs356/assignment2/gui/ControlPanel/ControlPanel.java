package edu.cs356.assignment2.gui.ControlPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import edu.cs356.assignment2.gui.AdminControlPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel{
	private static ControlPanel instance = null;
	private static int GAP = 5;
	//=========================================================
	// Constructor
	//=========================================================
	private ControlPanel() {
		//First set up the panel.
		setBackground(Color.GRAY);
		setPreferredSize(new Dimension((AdminControlPanel.WIDTH * 2 / 3) - GAP, AdminControlPanel.HEIGHT));
		setLayout(new GridLayout(3, 1, GAP, 0));	//Forces layout to be 3 row and 1 column
		
		//Add Button panels to this Main panel
		add(ControlPanelUserControl.getInstance());
		add(ControlPanelOpenUser.getInstance());
		add(ControlPanelShowControl.getInstance());
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Get the ControlPanel singleton instance. Uses lazy instantiation.
	 * @return	Instance of ControlPanel.
	 */
	public static ControlPanel getInstance() {
		if (instance == null)
			instance = new ControlPanel();
		return instance;
	}
}

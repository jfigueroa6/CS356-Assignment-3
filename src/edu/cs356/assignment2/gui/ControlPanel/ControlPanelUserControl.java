package edu.cs356.assignment2.gui.ControlPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import edu.cs356.assignment2.gui.AdminControlPanel;
import edu.cs356.assignment2.gui.TreeView.ACPTreeView;
import edu.cs356.assignment2.service.TwitterService;

@SuppressWarnings("serial")
public class ControlPanelUserControl extends JPanel {
	private static ControlPanelUserControl instance = null;	/**Holds static reference to an instance of this class*/
	
	private JButton addUser = new JButton("Add User"),
					addGroup = new JButton("Add Group"),
					removeUser = new JButton("Remove User"),
					removeGroup = new JButton("Remove Group");
	private JTextArea userID = new JTextArea("User ID", 1, 20),	/**TextArea with only 1 row and limit of 20 characters*/
						groupID = new JTextArea("Group ID", 1, 20);	/**TextArea with only 1 row and limit of 20 characters*/
	
	private AdminControlPanel acpSingleton;
	private ACPTreeView treeSingleton;
	private TwitterService service;
	//=========================================================
	// Constructor
	//=========================================================
	private ControlPanelUserControl() {
		acpSingleton = AdminControlPanel.getInstance();
		treeSingleton = ACPTreeView.getInstance();
		service = TwitterService.getInstance();
		
		//Set up the panel
		setBackground(getBackground());
		setPreferredSize(new Dimension(AdminControlPanel.WIDTH / 2,
				AdminControlPanel.HEIGHT / 3));	//Takes up 1/3 of the ControlPanel
		setLayout(new GridLayout(3, 2, 5, 5));	//Forces layout to have 3 rows and 2 columns and gaps of 5
		
		//Initialize ActionListeners
		initActionListeners();
		//Add buttons to panel
		initAddRemove();
	}
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds an ActionListener to the addUser button which tells TwitterService to add a user.
	 * If an error occurs, a dialog window appears.
	 */
	private void addListenerAddUser() {
		addUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Gets the group selected for the user from acpSingleton, and gets the new user ID
				//from the userID TextArea. If the addUserfunction fails, then display a dialog message
				if (service.addUser(userID.getText(), treeSingleton.getSelectedID()))
					//Operation successful so repaint the tree.
					treeSingleton.updateView();
				else
					//addUser failed so display a message
					JOptionPane.showMessageDialog(acpSingleton, "Error adding user. Check if selected ID is a user or"
							+ " new ID already exists.", "Add User Error", JOptionPane.ERROR_MESSAGE);
				userID.setText("User ID");
			}
		});
	}
	
	/**
	 * Adds an ActionListener to the addGroup button which tells TwiterService to add a group.
	 * If an error occurs, a dialog window is shown.
	 */
	private void addListenerAddGroup() {
		addGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Gets the parent group selected for the group from acpSingleton, and gets the new group ID
				//from the groupID TextArea. If the addGroup function fails, then display a dialog message
				if (service.addGroup(groupID.getText(), treeSingleton.getSelectedID()))
					//Operation successful so repaint the tree.
					treeSingleton.updateView();
				else
					//addGroup failed so display a message
					JOptionPane.showMessageDialog(acpSingleton, "Error adding group. Check if selected ID is a user or"
							+ " new ID already exists.", "Add Group Error", JOptionPane.ERROR_MESSAGE);
				groupID.setText("Group ID");
			}
		});
	}
	
	/**
	 * Adds an ActionListener to the removeUser button which tells TwitterService to remove a user.
	 * If an error occurs, a dialog window is shown.
	 */
	private void addListenerRemoveUser() {
		removeUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get the selected User ID from acpSingleton. If the removeUser method fails,
				//then a dialog message will be displayed.
				if (service.removeUser(treeSingleton.getSelectedID()))
					//Operation successful so repaint the tree
					treeSingleton.updateView();
				else
					//removeUser failed so display a message
					JOptionPane.showMessageDialog(acpSingleton, "Error removing ID: " 
							+ treeSingleton.getSelectedID() + ". Make sure it is a user ID.", "Remove User Error"
							, JOptionPane.ERROR_MESSAGE);
				userID.setText("User ID");	//BUG: Doing this because for some reason tree view won't reappear
			}
		});
	}
	
	/**
	 * Adds an ActionListener to the removeGroup button which tells TwitterService to remove a group.
	 * If an error occurs, a dialog window is shown.
	 */
	private void addListenerRemoveGroup() {
		removeGroup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Get the selected Group ID from acpSingleton. If the removeGroup method fails,
				//then a dialog message will be displayed.
				if (service.removeGroup(treeSingleton.getSelectedID()))
					//Operation successful so repaint the tree
					treeSingleton.updateView();
				else
					//removeGroup failed so display a message
					JOptionPane.showMessageDialog(acpSingleton, "Error removing ID: " 
							+ treeSingleton.getSelectedID() + ". Make sure it is a group ID and not root.", "Remove Group Error"
							, JOptionPane.ERROR_MESSAGE);
				groupID.setText("Group ID");	//BUG: Doing this because for some reason tree view won't reappear
			}
		});
	}
	
	/**
	 * Get the ControlPanelUserControl singleton instance. Uses lazy instantiation.
	 * @return	Instance of ControlPanel.
	 */
	public static ControlPanelUserControl getInstance() {
		if (instance == null)
			instance = new ControlPanelUserControl();
		return instance;
	}
	
	/**
	 * Initializes the ActionListeners for all of the buttons.
	 */
	private void initActionListeners() {
		addListenerAddUser();	//Initialize addUser ActionListener
		addListenerAddGroup();	//Initialize addGroup ActionListener
		addListenerRemoveUser(); //Initialize removeUser actionListener
		addListenerRemoveGroup();	//Initialize removeGroup ActionListener
	}
	
	/**
	 * Initializes the Add/Remove portion of the ControlPanel.
	 */
	private void initAddRemove() {
		//Add the objects buttons and text areas for this panel
		add(userID);
		add(addUser);
		add(groupID);
		add(addGroup);
		add(removeUser);
		add(removeGroup);
	}
}

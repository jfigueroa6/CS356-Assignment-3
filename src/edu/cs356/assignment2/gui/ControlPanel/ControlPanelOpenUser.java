package edu.cs356.assignment2.gui.ControlPanel;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cs356.assignment2.gui.AdminControlPanel;
import edu.cs356.assignment2.gui.TreeView.ACPTreeView;
import edu.cs356.assignment2.gui.UserView.UserView;
import edu.cs356.assignment2.service.TwitterService;
import edu.cs356.assignment2.service.TwitterUser.User;

@SuppressWarnings("serial")
public class ControlPanelOpenUser extends JPanel {
	private static ControlPanelOpenUser instance = null;	/**Holds static reference to an instance of this class*/
	private List<UserView> openUserViews = null;		/**Holds a reference to a list of all open user views*/
	
	private JButton openUserView = new JButton("Open User View");
	
	private AdminControlPanel acpSingleton;
	private ACPTreeView treeSingleton;
	private TwitterService service;
	//=========================================================
	// Constructor
	//=========================================================
	private ControlPanelOpenUser() {
		acpSingleton = AdminControlPanel.getInstance();
		treeSingleton = ACPTreeView.getInstance();
		service = TwitterService.getInstance();
		openUserViews = new LinkedList<UserView>();
		
		setBackground(getBackground());
		setPreferredSize(new Dimension(AdminControlPanel.WIDTH / 2,
				AdminControlPanel.HEIGHT / 3));	//Takes up 1/3 of the ControlPanel
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		//Initialize openUserView ActionListener
		addListenerUserView();
		//Add Button
		initOpenUser();
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds an ActionListener to the openUserView button which will open a new window with the selected
	 * user
	 */
	private void addListenerUserView() {
		openUserView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				User user = service.getUser(treeSingleton.getSelectedID());
				//If the ID is a user, then a user object is stored in user so create a new window.
				//Else show an error.
				if (user != null)
					openUserViews.add(new UserView(user));
				else
					JOptionPane.showMessageDialog(acpSingleton, "Selected ID is not a User.", "User ID Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
	
	/**
	 * Get the ControlPanelOpenUser singleton instance. Uses lazy instantiation.
	 * @return	Instance of ControlPanelOpenUser.
	 */
	public static ControlPanelOpenUser getInstance() {
		if (instance == null)
			instance = new ControlPanelOpenUser();
		return instance;
	}
	
	/**
	 * Initializes the Open User View portion of ControlPanel.
	 */
	private void initOpenUser() {
		//Add buttons
		add(Box.createRigidArea(new Dimension(0, 5)));
		add(openUserView);
		add(Box.createRigidArea(new Dimension(0, getPreferredSize().height - 50)));
	}
	
	/**
	 * Remove a view from the openUserViews list. Called when the UserView is disposed.
	 * @param view	View to remove.
	 */
	public void removeOpenView(JFrame view) {
		openUserViews.remove(view);
	}
	
	/**
	 * Refreshes any open user views to display any new messages for their followers.
	 */
	public void updateOpenViews() {
		for (UserView view : openUserViews)
			view.updateFrame();
	}
}

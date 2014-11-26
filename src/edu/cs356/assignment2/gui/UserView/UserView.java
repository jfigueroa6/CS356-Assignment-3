package edu.cs356.assignment2.gui.UserView;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.cs356.assignment2.gui.ControlPanel.ControlPanelOpenUser;
import edu.cs356.assignment2.service.TwitterUser.User;

@SuppressWarnings("serial")
public class UserView extends JFrame {
	public final static int HEIGHT = 425,
							WIDTH = 300;
	
	private JPanel mainPanel,
					tweetPanel;
	//=========================================================
	// Constructor
	//=========================================================
	/**
	 * Default constructor which takes in a user object. This object will
	 * be the basis for view.
	 * @param user
	 */
	public UserView(User user) {
		super("User: " + user.getID());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		//Create a panel to hold components
		initMainPanel(user);
		
		//Display the frame
		setVisible(true);
	}

	//=========================================================
	// Methods
	//=========================================================
	/**
	 * This method is called when the window close button is clicked. It removes
	 * itself from the open view list in the ControlPanelOpenUser. Finally,
	 * it closes the window.
	 */
	@Override
	public void dispose() {
		//Remove from the ControlPanelOpenuser open User view list.
		ControlPanelOpenUser.getInstance().removeOpenView(this);
		super.dispose();
	}
	
	/**
	 * Initializes the main panel with its subpanels which hold the buttons
	 * and lists.
	 */
	private void initMainPanel(User user) {
		mainPanel = new JPanel();
		
		//Setup main panel
		mainPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		mainPanel.setLayout(new GridLayout(2, 1, 5, 3));
		
		//Add subpanels
		mainPanel.add(new FollowPanel(user));
		tweetPanel = new NewsFeedPanel(user);
		mainPanel.add(tweetPanel);
		
		//Add panel to this frame, and pack everything in.
		getContentPane().add(mainPanel);
		pack();
	}
	
	/**
	 * Called to update the news feed. This is usually done when another user view is updated.
	 */
	public void updateFrame() {
		((NewsFeedPanel)tweetPanel).changedNewsFeed();
	}
}

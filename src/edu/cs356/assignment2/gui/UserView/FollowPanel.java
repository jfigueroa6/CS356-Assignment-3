package edu.cs356.assignment2.gui.UserView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import edu.cs356.assignment2.service.TwitterService;
import edu.cs356.assignment2.service.TwitterUser.User;

@SuppressWarnings("serial")
public class FollowPanel extends JPanel {
	private User user;
	private String selectedID = null;

	private JScrollPane following;	//List of users being followed	
	private JTextArea followID = new JTextArea("Follow ID");
	private JButton followUser = new JButton("Follow User"),
					unfollowUser = new JButton("Unfollow User");
	
	private TwitterService service;
	//=========================================================
	// Constructor
	//=========================================================
	public FollowPanel(User user) {
		service = TwitterService.getInstance();
		this.user = user;
		
		//Setup panel
		setPreferredSize(new Dimension(UserView.WIDTH, UserView.HEIGHT / 2));
		setLayout(new BorderLayout(2, 2));
		
		//Initialize ActionListeners
		addFollowUserListener();
		addUnfollowUserListener();
		
		//Initialize the first following list
		updateFollowingList();	
		
		//Add the components to this panel
		add(followUser, BorderLayout.WEST);
		add(unfollowUser, BorderLayout.EAST);
		add(followID, BorderLayout.NORTH);	
		add(following, BorderLayout.SOUTH);
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds a ListSelectionListener to the given JList list. This listener changes
	 * selectedID to the selected list item. This is used as a precursor to unfollowing
	 * a user.
	 * @param list	List to add Listener to.
	 */
	private void addFollowingListListener(JList<String> list) {
		list.addListSelectionListener(new ListSelectionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				selectedID = ((JList<String>)arg0.getSource()).getSelectedValue();
			}	
		});
	}
	
	/**
	 * Adds an ActionListener to the followUser button. This will take a user ID from
	 * the userID JTextArea, and the TwitterService addFollowing method will be called.
	 * If an error occurs, such as an incorrect ID, an error dialog window will be displayed.
	 */
	private void addFollowUserListener() {
		followUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Call the addFollowing method in twitterService. If it returns true, update the
				//following list. If false, show an error.
				if (service.follow(followID.getText(), user.getID())) {
					changedFollowing();
				}
				else
					JOptionPane.showMessageDialog(following, "Error with ID. Might be a Group ID or does not exist.",
							"Follow ID Error", JOptionPane.ERROR_MESSAGE);			
			}	
		});
	}
	
	/**
	 * Adds an ActionListener to the unfollowUser button. This will take a user ID selected from the following list
	 * and the TwitterService unfollowUser will be called. If this method is successful the following list will be
	 * updated to show the change, and the selected ID will be set back to null. Else, a dialog message will be display
	 * giving the user some info on how to unfollow another user.
	 */
	private void addUnfollowUserListener() {
		unfollowUser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//First make sure an ID was selected. Then if the unfollowUser method from the service succeeds, refresh the following list
				//else display a dialog window
				if (selectedID != null && service.unfollow(selectedID, user.getID())) {
					changedFollowing();
					selectedID = null;		//No ID selected so set it to null
				}
				else
					JOptionPane.showMessageDialog(following, "A user ID must be selected from the list.",
							"Unfollow User Info", JOptionPane.INFORMATION_MESSAGE);	
			}	
		});
	}
	
	/**
	 * Called when a change to following users has happened. For example, followng or unfollowing a user.
	 */
	private void changedFollowing() {
		remove(following);
		updateFollowingList();
		add(following, BorderLayout.SOUTH);
		followID.setText("Follow ID");
		following.revalidate();
		following.repaint();
	}
	
	/**
	 * Updates the following list to show any changes to the users being followed.
	 */
	private void updateFollowingList() {
		//Setup the list by copying the following IDs from user
		JList<String> list = new JList<String>(Arrays.copyOf(user.getFollowing().toArray(), user.getFollowing().size(), String[].class));
		list.setSelectionMode(1);	//Allow only one item to be selected
		list.setLayoutOrientation(JList.VERTICAL);
		list.setPreferredSize(new Dimension(getWidth(), getHeight() * 2/3));
		//Add the listener to the list
		addFollowingListListener(list);
		//Add scrollbars
		following = new JScrollPane(list);
	}
}

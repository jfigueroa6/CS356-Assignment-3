package edu.cs356.assignment2.gui.UserView;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.cs356.assignment2.gui.ControlPanel.ControlPanelOpenUser;
import edu.cs356.assignment2.service.TwitterUser.Tweet;
import edu.cs356.assignment2.service.TwitterUser.User;

@SuppressWarnings("serial")
public class NewsFeedPanel extends JPanel {
	private User user;
	private JTextArea tweetInput = new JTextArea("Tweet Message");	//Where tweets are input
	private JButton postTweet = new JButton("Post Tweet");	//Post tweet to user's and follower's news feed
	private JScrollPane newsFeed;	//Holds the news feed
	
	//=========================================================
	// Constructor
	//=========================================================
	public NewsFeedPanel(User user) {
		this.user = user;
		
		//Setup panel
		setPreferredSize(new Dimension(UserView.WIDTH, UserView.HEIGHT / 2));
		setLayout(new BorderLayout(2, 2));
		
		//Initialize ActionListener
		addPostTweetListener();
		
		//Initialize News Feed
		updateNewsFeed();
		
		//Add components to this panel
		add(tweetInput, BorderLayout.CENTER);
		add(postTweet, BorderLayout.EAST);
		add(newsFeed, BorderLayout.SOUTH);
	}
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds an ActionListener to the postTweet button. This button calls the
	 * postTweet method in user and updates the panel's newsFeed list. Finally,
	 * it tells the ControlPanelOpenUser class to update any opened user views.
	 */
	private void addPostTweetListener() {
		postTweet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//Post the tweet to the user's news feed, refresh the news feed view,
				//and update all other views.
				user.postTweet(tweetInput.getText());
				changedNewsFeed();
				ControlPanelOpenUser.getInstance().updateOpenViews();
			}		
		});
	}
	
	/**
	 * Called when the newsFeed is changed.
	 */
	public void changedNewsFeed() {
		remove(newsFeed);
		updateNewsFeed();
		add(newsFeed, BorderLayout.SOUTH);
		tweetInput.setText("Tweet Message");
		newsFeed.revalidate();
		newsFeed.repaint();
	}
	
	/**
	 * Updates the news feed to display new messages that were posted by the user or those user's being followed.
	 */
	private void updateNewsFeed() {
		//Create a JList from the news feed list and setup a few list options
		JList<Tweet> list = new JList<Tweet>(Arrays.copyOf(user.getNewsFeed().toArray(), user.getNewsFeed().size(), Tweet[].class));
		list.setSelectionMode(0);	//Don't allow any selections
		list.setLayoutOrientation(JList.VERTICAL);
		//Add scrollbars and assign to newsFeed
		newsFeed = new JScrollPane(list);
	}
}

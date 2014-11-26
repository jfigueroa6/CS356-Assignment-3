package edu.cs356.assignment2.service.TwitterUser;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class User extends Observable implements Observer {
	private List<String> followers,		/**List of ID's of followers*/
						 following;		/**List of ID's of users being followed*/
	private String id;					/**User id*/
	private List<Tweet> newsFeed;		/**News feed containing user and following tweets*/
	
	//=========================================================
	// Constructor
	//=========================================================
	/**
	 * Creates a user with the given ID, and then initializes that user's followers, following,
	 * and news feed list. NOTE: This does not check for a unique ID among users.
	 * @param id	ID of user.
	 */
	public User(String id) {
		super();
		this.id = id;
		followers = new LinkedList<String>();
		following = new LinkedList<String>();
		newsFeed = new LinkedList<Tweet>();
	}
	
	//=========================================================
	// Data Members
	//=========================================================
	/**
	 * Adds a follower's id to the user's followers list. The user that is following
	 * has this user's id added to their following list.
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void addObserver(Observer arg0) {
		//First make sure you're not trying to follow yourself.
		//That's just narcissistic.
		if (this == arg0)
			return;
		
		//Add to followers list, add my id to other user's following list,
		// and add as an observer
		followers.add(((User)arg0).id);
		((User)arg0).following.add(id);
		super.addObserver(arg0);
	}
	
	/**
	 * Deletes a follower from the followers list, and the deleted follower
	 * has this user ID removed from its following list. Finally, all of this
	 * user's tweets are removed from the followers news feed.
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void deleteObserver(Observer arg0) {
		//You also can't unfollow yourself. If you try then
		//you need a psychiatrist.
		if (this == arg0)
			return;
		followers.remove(((User)arg0).id);
		((User)arg0).following.remove(id);
		
		//Remove from Observer's news feed
		((User)arg0).removeFromNewsFeed(id);
		
		super.deleteObserver(arg0);
	}
	
	/**
	 * Clears the followers list and notifies all observers that they have been
	 * deleted.
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void deleteObservers() {
		followers.clear();
		notifyObservers(-1); 		//-1 is used to tell observers to remove user from following list
		super.deleteObservers();
	}
	
	/**
	 * Get the IDs of followers.
	 * @return	Follower's IDs
	 */
	public List<String> getFollowers() {
		return followers;
	}
	
	/**
	 * Get IDs of users being followed by this user.
	 * @return	IDs of users being followed
	 */
	public List<String> getFollowing() {
		return following;
	}
	
	/**
	 * Get User ID
	 * @return	User ID
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Get this user's news feed.
	 * @return	News Feed
	 */
	public List<Tweet> getNewsFeed() {
		return newsFeed;
	}
	
	/**
	 * Post a tweet to this user's news feed. Any followers
	 * are notified and their news feed is updated to include the feed.
	 * @param msg
	 */
	public void postTweet(String msg) {
		Tweet tweet = new Tweet(id, msg);
		newsFeed.add(tweet);
		setChanged();
		notifyObservers(tweet);
	}
	
	/**
	 * Removes a user's tweets from this user's news feed. This is used when
	 * this user stops following a user.
	 * @param userID	ID of user whose tweets will be removed.
	 */
	private void removeFromNewsFeed(String userID) {
		Iterator<Tweet> i = newsFeed.iterator();
		while (i.hasNext()) {
			Tweet t = i.next();
			if (t.getID().equals(userID))
				i.remove();
		}
	}
	
	/**
	 * Updates a user when it receives a notification that a follower has done a change.
	 * If the received Object is an Integer, then that user will be removed from the following
	 * list and news feed. Else, it is a tweet, and it will be posted to the news feed.
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		//	The passed arg1 is an integer so delete the observable object from
		// the following list. This is used when a user deletes all of its followers
		if (arg1 instanceof Integer) {
			following.remove(((User)arg0).id);
			//User we were following has removed us, so remove them from our news feed
			removeFromNewsFeed(((User)arg0).id);
		}
		//The notification is a tweet so post it to our news feed
		else {
			newsFeed.add((Tweet)arg1);
		}
	}
}

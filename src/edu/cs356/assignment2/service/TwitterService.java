package edu.cs356.assignment2.service;

import edu.cs356.assignment2.gui.Visitor.Visitor;
import edu.cs356.assignment2.service.TwitterGroupTree.TwitterGroupTree;
import edu.cs356.assignment2.service.TwitterUser.User;

public class TwitterService {
	private static TwitterService instance = null;	/**TwitterService singleton*/
	private TwitterGroupTree treeSingleton;
	//=========================================================
	// Constructor
	//=========================================================
	/**
	 * Initializes the TwitterService. Is private since this class is a singleton.
	 */
	private TwitterService() {
		treeSingleton = TwitterGroupTree.getInstance();
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Accepts a visitor object from the GUI, and sends it on to the group tree.
	 * @param v	Visiting object.
	 */
	public void accept(Visitor v) {
		treeSingleton.accept(v);
	}
	/**
	 * Add group to tree.
	 * @param groupID	ID of new group.
	 * @return	True if operation was successful, false otherwise.
	 */
	public boolean addGroup(String groupID) {
		return treeSingleton.addGroup(groupID);
	}
	
	/**
	 * Add group to the tree under another group.
	 * @param groupID	ID of new group.
	 * @param parentID	ID of parent group
	 * @return	True if operation was successful, false otherwise.
	 */
	public boolean addGroup(String groupID, String parentID) {
		return treeSingleton.addGroup(groupID, parentID);
	}
	
	/**
	 * Add a user to the tree.
	 * @param userID	ID of new user.
	 * @return	True if operation is successful, false otherwise.
	 */
	public boolean addUser(String userID) {
		return treeSingleton.addUser(userID);
	}
	
	/**
	 * Add a user to the given group.
	 * @param userID	ID of new user.
	 * @param groupID	ID of group that user will belong to.
	 * @return	True if operation is successful, false otherwise.
	 */
	public boolean addUser(String userID, String groupID) {
		return treeSingleton.addUser(userID, groupID);
	}
	
	/**
	 * A user (followerID) wants to follow another user (subjectID).
	 * @param subjectID	ID of user to follow.
	 * @param followerID	ID of follower
	 * @return	True if operation is successful, false otherwise
	 */
	public boolean follow(String subjectID, String followerID) {
		return followUnfollow(subjectID, followerID, true);
	}
	
	/**
	 * Since follow and unfollow are the same except for one line, just use the same
	 * method to handle both.
	 * @param subjectID	ID of user to follow/unfollow.
	 * @param followerID	ID of follower
	 * @param follow	If true then performs follow, else performs unfollow operation
	 * @return	True if operation successful, false otherwise.
	 */
	private boolean followUnfollow(String subjectID, String followerID, boolean follow) {
		//User wants to follow him/herself. Isn't that narcissistic.
		if (subjectID.equalsIgnoreCase(followerID))
			return false;
		
		User subject = treeSingleton.getUser(subjectID);
		User follower = treeSingleton.getUser(followerID);
		//If subject or follower are null then return false
		if (subject == null || follower == null)
			return false;
		
		//If follow is true, then it adds an observer, otherwise it removes an observer.
		if (follow)
			//Add follower as an observer of subject
			subject.addObserver(follower);
		else
			//Remove follower from being an observer of subject
			subject.deleteObserver(follower);
		
		return true;
	}
	
	public User getUser(String userID) {
		return treeSingleton.getUser(userID);
	}
	
	/**
	 * Static method to access the TwitterService singleton instance. Uses
	 * lazy instantiation, so memory is only used when this method is first
	 * called.
	 * @return	Instance of TwitterService
	 */
	public static TwitterService getInstance() {
		//If the instance has not been initialized
		//then initialize it.
		if (instance == null)
			instance = new TwitterService();
		return instance;
	}
	
	/**
	 * Remove a group from the tree
	 * @param groupID	ID of group to remove
	 * @return	True if removal successful, false otherwise.
	 */
	public boolean removeGroup(String groupID) {
		return treeSingleton.removeGroup(groupID);
	}
	
	/**
	 * Remove a user.
	 * @param userID	ID of user to delete.
	 * @return	True if operation successful, false otherwise.
	 */
	public boolean removeUser(String userID) {
		return treeSingleton.removeUser(userID);
	}
	
	/**
	 * User with followerID stops following user with subjectID.
	 * @param subjectID	ID of subject to stop following.
	 * @param followerID	ID of follower.
	 * @return	True if operation successful, false otherwise.
	 */
	public boolean unfollow(String subjectID, String followerID) {
		return followUnfollow(subjectID, followerID, false);
	}
}

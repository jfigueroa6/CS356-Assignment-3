package edu.cs356.assignment2.service.TwitterGroupTree;

import edu.cs356.assignment2.gui.Visitor.Visitor;
import edu.cs356.assignment2.service.TwitterUser.User;

public class TwitterGroupTree {
	private static TwitterGroupTree instance = null;	/**Holds the reference to this singleton instance*/
	private GroupComponents root;		/**Holds the root group of the tree*/
	//=========================================================
	// Constructor
	//=========================================================
	private TwitterGroupTree() {
		root = new UserGroup("Root");
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Accepts a visiting object and sends it to the root for it
	 * to do its task.
	 * @param v	Visiting object
	 */
	public void accept(Visitor v) {
		root.accept(v);
	}
	
	/**
	 * Adds a new group to the Root user group.
	 * @param groupID	ID of new group
	 * @return	True if operation successful, false otherwise.
	 */
	public boolean addGroup(String groupID) {
		//Just call the other addGroup method with parentGroup of
		//"Root"
		return addGroup(groupID, "Root");
	}
	
	/**
	 * Add a new group with the parentGroup as the parent
	 * @param groupID	ID of new Group
	 * @param parentGroup	Parent of new group
	 * @return	True if operation successful, false otherwise
	 */
	public boolean addGroup(String groupID, String parentGroup) {
		//First check that the new Group's ID is unique. If not, return false
		if (root.checkID(groupID))
			return false;
		
		//Get the parent group and make sure it is a group and not a user.
		GroupComponents parent = root.getComponent(parentGroup);
		if (parent instanceof UserGroup)
			parent.add(new UserGroup(groupID));
		//Trying to add a group to a user. Can't do that.
		else
			return false;
		
		//AddGroup was successful
		return true;
	}
	
	/**
	 * Adds a new user to the root group.
	 * @param userID	New user ID
	 * @return		True if operation successful, false otherwise.
	 */
	public boolean addUser(String userID) {
		//Just call the other addUser method.
		return addUser(userID, "root");
	}
	
	/**
	 * Adds a new user to the given group
	 * @param userID	New user ID
	 * @param groupID	Group ID to add user to.
	 * @return		True if operation successful, false otherwise
	 */
	public boolean addUser(String userID, String groupID) {
		//First make sure that the new user ID does not exist
		if (root.checkID(userID))
			return false;
		
		//Get the UserGroup and make sure it's not null and that it's actually a group
		GroupComponents group = root.getComponent(groupID);
		if ((group != null) && (group instanceof UserGroup))
			//Add user to this group
			group.add(new GroupComponentsUser(new User(userID)));
		else
			//GroupID referred to a user or that group doesn't exist
			return false;
		
		//Congratulations you joined this Twitter knock-off!!!
		return true;
	}
	
	/**
	 * Static method to access the singleton instance of TwitterGroupTree.
	 * @return	TwitterGroupTree instance
	 */
	public static TwitterGroupTree getInstance() {
		if (instance == null)
			instance = new TwitterGroupTree();
		return instance;
	}
	
	/**
	 * Get the given user.
	 * @param userID	ID of user.
	 * @return	User if found.
	 */
	public User getUser(String userID) {
		return root.getUser(userID);
	}
	
	/**
	 * Remove the given group and shift all children to the parent. Will NOT
	 * allow the root to be deleted.
	 * @param groupID	Group to delete.
	 * @return		True if removal is successful, false otherwise.
	 */
	public boolean removeGroup(String groupID) {
		//First check if the group exists and that it is not the root group.
		if (groupID.equalsIgnoreCase("root") || !(root.checkID(groupID)))
			return false;
		
		//Get the group and make sure it's not a user. If it's a user then return false.
		GroupComponents group = root.getComponent(groupID);
		if (group instanceof UserGroup) {
			//Since only UserGroups can have children we're assuming it is a UserGroup.
			GroupComponents parent = group.getParent();
			//Add all of this group's children to the parent group
			for (int i = 0; i < group.getNumChild(); i++) {
				parent.add(group.getChild(i));
			}
			//Remove group from parent. This child is disowned.
			parent.remove(group);
		}
		else
			return false;
		
		//Successful removal of group
		return true;
	}
	
	/**
	 * Removes a user from the tree. This pretty much deletes a user.
	 * @param userID	ID of user to delete.
	 * @return	True if the operation was successful, false otherwise.
	 */
	public boolean removeUser(String userID) {
		//First check that the user exists
		if (!(root.checkID(userID)))
			return false;
		
		//Get the user. If it's null return false (this means the ID is for a group).
		GroupComponents leaf = root.getComponent(userID);
		User user = leaf.getUser(userID);
		if (user != null) {
			//Find all users that this user is following and remove this user from their followers list.
			for (String following : user.getFollowing())
				root.getUser(following).deleteObserver(user);
			//Clear the user's followers
			user.deleteObservers();
			//Remove user leaf component
			leaf.getParent().remove(leaf);
		}
		else
			return false;
		
		//Success removing user
		return true;
	}
}

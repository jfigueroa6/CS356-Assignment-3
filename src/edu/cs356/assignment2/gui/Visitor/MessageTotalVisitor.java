package edu.cs356.assignment2.gui.Visitor;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;
import edu.cs356.assignment2.service.TwitterUser.User;

public class MessageTotalVisitor implements Visitor {
	private int count = 0;
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Get the total number of messages.
	 * @return	Number of messages
	 */
	public int getMsgCount() {
		return count;
	}
	
	/**
	 * Gets the total number of messages this leaf/user has on their
	 * news feed.
	 * @param leaf	User
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		//Get user and the size of their news feed.
		User temp = leaf.getUser(leaf.getID());
		count += temp.getNewsFeed().size();
	}
	
	/**
	 * Visits the children of the group to find any leaf/user and get
	 * their news feed size.
	 * @param group
	 */
	@Override
	public void visitUserGroup(UserGroup group) {
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);

	}

}

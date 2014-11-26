package edu.cs356.assignment2.gui.Visitor;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;

public class UserTotalVisitor implements Visitor {
	private int count = 0;
	
	//=========================================================
	// Methods
	//=========================================================
	public int getUserTotal() {
		return count;
	}
	
	/**
	 * It's a leaf, so increase the count.
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		count++;

	}
	
	/**
	 * Go through all children of the group.
	 */
	@Override
	public void visitUserGroup(UserGroup group) {
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);
	}

}

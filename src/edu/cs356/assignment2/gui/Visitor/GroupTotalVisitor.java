package edu.cs356.assignment2.gui.Visitor;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;

public class GroupTotalVisitor implements Visitor {
	private int count = 0;
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Get the number of groups counted.
	 * @return	Number of groups
	 */
	public int getGroupCount() {
		return count;
	}
	
	/**
	 * A GroupComponentUser calls this, but it is a NOP since only UserGroups
	 * are being called.
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		//NOP: Don't want to do this, but the only other way is to create a tag class with User count
	}

	@Override
	public void visitUserGroup(UserGroup group) {
		//Count this group
		count++;
		
		//Then go through all of the children to count child groups
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);
	}

}

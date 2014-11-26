package edu.cs356.assignment2.gui.Visitor;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;

public interface Visitor {
	public void visitGroupComponentsUser(GroupComponentsUser leaf);
	public void visitUserGroup(UserGroup group);
}

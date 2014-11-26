package edu.cs356.assignment2.gui.Visitor;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;

public class IDValidationVisitor implements Visitor {
	private boolean valid = true;	/**Holds if the tree has valid ID's. Assumes it is initially true*/
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Return the determined validity of the IDs.
	 * @return
	 */
	public boolean getValidity() {
		return valid;
	}
	
	/**
	 * Check the leaf's ID validity
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		//No need to check for copies, since the service already prevents 
		//that, so check if the ID contains spaces.
		if (leaf.getID().contains(" "))
			valid = false;
	}
	
	/**
	 * First check the validity of the group ID, then go through all children of the group.
	 */
	@Override
	public void visitUserGroup(UserGroup group) {
		if (group.getID().contains(" "))
			valid = false;
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);
	}

}

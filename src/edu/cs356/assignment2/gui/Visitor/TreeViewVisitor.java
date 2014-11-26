package edu.cs356.assignment2.gui.Visitor;

import javax.swing.tree.DefaultMutableTreeNode;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;

public class TreeViewVisitor implements Visitor {
	private DefaultMutableTreeNode root = null;		/**Root node of tree*/
	private DefaultMutableTreeNode parent = null;	/**Used for attaching subgroups to correct parent group*/
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Returns the root node of this tree.
	 * @return	Root Node
	 */
	public DefaultMutableTreeNode getRoot() {
		//If the root is null, then return null since this visitor hasn't
		//been sent on his super secret mission.
		if (root == null)
			return null;
		return root;
	}
	
	/**
	 * For a GroupComponentUser (a user), the user ID is added to
	 * the parent.
	 * {@inheritDoc}
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		//Create a new tree node with this component's id and attach it to parent.
		parent.add(new DefaultMutableTreeNode(leaf.getID()));
	}

	/**
	 * For a UserGroup, a corresponding category is created. The children are sent the 
	 * visitor, and when they are done, the category has its children attached. Finally,
	 * the category is attached to its parent category (if it has one).
	 * {@inheritDoc}
	 */
	@Override
	public void visitUserGroup(UserGroup group) {
		DefaultMutableTreeNode category = null;
		//If root is null, create the root node and assign to category. Else,
		// just assign to category. Root node is the basis for the entire "tree".
		if (root == null)
			root = category = new DefaultMutableTreeNode(group.getID());
		else
			category = new DefaultMutableTreeNode(group.getID());
		
		//Go through each child and add them under category. First make sure to set parent.
		DefaultMutableTreeNode tempParent = parent;		//Holds parent so it doesn't get overwritten in the recursive call.
		parent = category;	//This category becomes the new parent
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);
		
		//Reassign parent with the old parent and add category to the parent only if it has a parent.
		parent = tempParent;
		if (parent != null)
			parent.add(category);
	}

}

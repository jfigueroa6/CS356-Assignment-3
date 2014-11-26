package edu.cs356.assignment2.service.TwitterGroupTree;

import java.util.ArrayList;
import java.util.List;

import edu.cs356.assignment2.gui.Visitor.Visitor;
import edu.cs356.assignment2.service.TwitterUser.User;

public class UserGroup implements GroupComponents{
	private String id;		/**Group ID*/
	private List<GroupComponents> children = null;	/**List of child GroupComponents*/
	private GroupComponents parent = null;
	
	//=========================================================
	// Constructor
	//=========================================================
	/**
	 * Constructor that establishes UserGroup ID and initializes children list.
	 * @param id	Group ID
	 */
	public UserGroup(String id) {
		this.id = id;
		children = new ArrayList<GroupComponents>();
	}
	
	//=========================================================
	// Methods
	//=========================================================
	@Override
	public void add(GroupComponents grComp) {
		if (grComp != null) {
			//Add this object as parent of child
			grComp.setParent(this);
			children.add(grComp);
		}
	}

	@Override
	public void remove(GroupComponents grComp) {
		if (grComp != null) {
			//Abandon this child. Just like a horrible parent.
			grComp.setParent(null);
			children.remove(grComp);
		}
	}

	@Override
	public GroupComponents getChild(int child) {
		if (child >= 0 && child < children.size())
			return children.get(child);
		return null;
	}

	@Override
	public int getNumChild() {
		return children.size();
	}
	
	/**
	 * This class will check its ID followed by the child IDs.
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkID(String id) {
		//First check own ID
		if (this.id.equalsIgnoreCase(id))
			return true;
		
		//Now check child IDs
		for (GroupComponents child : children) {
			//If the child ID matches the given id then return true.
			if (child.checkID(id))
				return true;
		}
		
		//No match for id
		return false;
	}
	
	/**
	 * This class will check its ID first, then if no result, it will check
	 * its children.
	 * {@inheritDoc}
	 */
	@Override
	public GroupComponents getComponent(String id) {
		//First check own ID
		if (this.id.equalsIgnoreCase(id))
			return this;
		
		//Check the children
		for (GroupComponents child : children) {
			//Get result this way because may have nested group components.
			GroupComponents result = child.getComponent(id);
			//If the result is not null, then found an answer
			if (result != null)
				return result;
		}
		return null;
	}

	@Override
	public void setParent(GroupComponents parent) {
		this.parent = parent;
	}

	@Override
	public GroupComponents getParent() {
		return parent;
	}
	
	/**
	 * Since only GroupComponentsUserLeafs hold users, have UserGroup search their children.
	 * {@inheritDoc}
	 */
	@Override
	public User getUser(String id) {
		//Since UserGroup doesn't have children, search the children of Leaf components
		for (GroupComponents child : children) {
			User user = child.getUser(id);
			if (user != null)
				return user;
		}
		//This group does not have this user.
		return null;
	}
	
	/**
	 * Calls the visitUserGroup method in the visitor.
	 * {@inheritDoc}
	 */
	@Override
	public void accept(Visitor v) {
		v.visitUserGroup(this);		
	}

	@Override
	public String getID() {
		return id;
	}
}

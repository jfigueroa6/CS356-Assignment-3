package edu.cs356.assignment2.service.TwitterGroupTree;

import edu.cs356.assignment2.gui.Visitor.Visitor;
import edu.cs356.assignment2.service.TwitterUser.User;

public interface GroupComponents {
	/**
	 * Adds a GroupComponent to this object.
	 * @param grComp	Component to add
	 */
	public void add(GroupComponents grComp);
	/**
	 * Removes a GroupComponent from this object.
	 * @param grComp	Object to remove
	 */
	public void remove(GroupComponents grComp);
	/**
	 * Gets a child GroupComponent dictated by child.
	 * @param child	Component to retrieve.
	 * @return	Child component
	 */
	public GroupComponents getChild(int child);
	/**
	 * Get number of children that object has.
	 * @return	Number of children
	 */
	public int getNumChild();
	/**
	 * Compares the component's ID to the given id, and returns 
	 * if the given id exists or not.
	 * @param id	ID to check for.
	 * @return		True if the ID exists. False otherwise.
	 */
	public boolean checkID(String id);
	/**
	 * Gets the component with the given id.
	 * @param id	ID of component to retrieve.
	 * @return		Component if it exists or null
	 */
	public GroupComponents getComponent(String id);
	/**
	 * Sets the parent of the component.
	 * @param parent	Parent component
	 */
	public void setParent(GroupComponents parent);
	/**
	 * Gets the parent of the component
	 * @return	Parent component
	 */
	public GroupComponents getParent();
	
	/**
	 * Finds a user with the given ID and returns them
	 * @param id	ID of user
	 * @return		User object
	 */
	public User getUser(String id);
	
	/**
	 * Accepts a visiting object.
	 * @param v	Visiting object.
	 */
	public void accept(Visitor v);
	
	/**
	 * Get the ID of the GroupComponent.
	 * @return	ID of Component.
	 */
	public String getID();
}

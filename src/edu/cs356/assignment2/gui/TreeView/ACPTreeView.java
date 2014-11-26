package edu.cs356.assignment2.gui.TreeView;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeSelectionModel;

import edu.cs356.assignment2.gui.AdminControlPanel;
import edu.cs356.assignment2.gui.Visitor.TreeViewVisitor;
import edu.cs356.assignment2.service.TwitterService;

@SuppressWarnings("serial")
public class ACPTreeView extends JScrollPane {
	private static ACPTreeView instance = null;
	
	private JTree tree = null;
	private String selectedID = "root";		/**Holds the ID selected on tree. Default of root just in case none is selected*/
	
	private TwitterService service;
	//=========================================================
	// Constructor
	//=========================================================
	private ACPTreeView() {
		service = TwitterService.getInstance();
		
		//Setup pane
		setPreferredSize(new Dimension((AdminControlPanel.WIDTH / 3) - 5, AdminControlPanel.HEIGHT));
		
		updateTreeView();
	}
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds a listener to the tree. When an item on the treeview is selected, the ID of this selection is stored
	 * in selectedID.
	 */
	private void addTreeSelectionListener() {
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent arg0) {
				selectedID = arg0.getPath().getLastPathComponent().toString();
			}
		});
	}
	
	/**
	 * Expands the nodes that are groups so that they always show their children.
	 */
	private void expandGroups() {
		for(int i = 0; i < tree.getRowCount(); i++)
			tree.expandRow(i);
	}
	
	/**
	 * Get the TreeView singleton instance. Uses lazy instantiation.
	 * @return	Instance of TreeView.
	 */
	public static ACPTreeView getInstance() {
		if (instance == null)
			instance = new ACPTreeView();
		return instance;
	}
	
	/**
	 * Gets the ID that was selected on the tree view.
	 * @return	Selected ID
	 */
	public String getSelectedID() {
		return selectedID;
	}
	
	/**
	 * Updates the tree view by sending a visitor to the TwitterService. Once it returns, the tree is
	 * developed from the result, and it is stored into the tree view. The size is set to half the width
	 * of the parent panel.
	 * @param initialize	If true, then this call is part of init, and won't remove treeView from mainPanel.
	 */
	private void updateTreeView() {
		//Send the visitor on its super secret mission to create a tree from the user and group ID's
		TreeViewVisitor visitor = new TreeViewVisitor(); 
		service.accept(visitor);
		
		//TODO: Change the icons for the tree. Groups that do not have any children look like users.
		
		//Set the selection mode and the selection listener.
		tree = new JTree(visitor.getRoot());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	//Can only select one item in the tree
		addTreeSelectionListener();
		expandGroups();
		
		//Set the JScrollPane view to the tree
		setViewportView(tree);
	}
	
	/**
	 * Updates the Tree View. In particular, it updates the tree view to display new users and
	 * groups. It also resets selectedID to root
	 */
	public void updateView() {
		updateTreeView();
		selectedID = "root";
	}
	
	
}

package edu.cs356.assignment2.gui.ControlPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.cs356.assignment2.gui.AdminControlPanel;
import edu.cs356.assignment2.gui.Visitor.GroupTotalVisitor;
import edu.cs356.assignment2.gui.Visitor.MessageTotalVisitor;
import edu.cs356.assignment2.gui.Visitor.PositiveMsgVisitor;
import edu.cs356.assignment2.gui.Visitor.UserTotalVisitor;
import edu.cs356.assignment2.service.TwitterService;

@SuppressWarnings("serial")
public class ControlPanelShowControl extends JPanel {
	private static ControlPanelShowControl instance = null;	/**Holds static reference to an instance of this class*/
	
	private JButton showGroupTotal = new JButton("Show Group Total"),
					showMsgTotal = new JButton("Show Message Total"),
					showPosPerc = new JButton("Show Positive Percentage"),
					showUserTotal = new JButton("Show User Total");
	
	private AdminControlPanel acpSingleton;
	private TwitterService service;
	//=========================================================
	// Constructor
	//=========================================================
	private ControlPanelShowControl() {
		acpSingleton = AdminControlPanel.getInstance();
		service = TwitterService.getInstance();
		
		setBackground(getBackground());
		setPreferredSize(new Dimension(AdminControlPanel.WIDTH / 2,
				AdminControlPanel.HEIGHT / 3));	//Takes up 1/3 of the ControlPanel
		setLayout(new GridLayout(2, 2, 5, 5));
		
		//Initialize the ActionListeners
		initActionListeners();
		//Add Buttons
		initShowButtons();
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Adds an ActionListener to the showGroupTotal button which counts the number of groups. It then displays
	 * the result in a dialog window.
	 */
	private void addListenerShowGroupTotal() {
		showGroupTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GroupTotalVisitor v = new GroupTotalVisitor();
				//Count the number of users and display dialog box with results
				service.accept(v);
				JOptionPane.showMessageDialog(acpSingleton, "Number of Groups: " + v.getGroupCount(), "Group Count"
						, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	//TODO: Testing on ShowMsgTotal
	/**
	 * Adds an ActionListener to the showMsgTotal button which counts the number of messages. It then displays
	 * the result in a dialog window.
	 */
	private void addListenerShowMsgTotal() {
		showMsgTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				MessageTotalVisitor v = new MessageTotalVisitor();
				//Count the number of users and display dialog box with results
				service.accept(v);
				JOptionPane.showMessageDialog(acpSingleton, "Number of Tweets: " + v.getMsgCount(), "Tweet Count"
						, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	//TODO: Test this
	/**
	 * Adds an ActionListener to the showPosPerc button which gets a percentage of positive messages. It then displays
	 * the result in a dialog window. If it cannot open the positive_words file, an error message will appear.
	 */
	private void addListenerShowPosPerc() {
		showPosPerc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					PositiveMsgVisitor v = new PositiveMsgVisitor();
					//Count the number of users and display dialog box with results
					service.accept(v);
					JOptionPane.showMessageDialog(acpSingleton, "Percent of Positive Tweets: " + v.getPositivePercentage() + "%", 
							"Positive Tweet Percentage", JOptionPane.INFORMATION_MESSAGE);
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(acpSingleton, "Error openining positive_words.txt", 
							"File Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}

	/**
	 * Adds an ActionListener to the showUserTotal button which counts the number of users. It then displays
	 * the result in a dialog window.
	 */
	private void addListenerShowUserTotal() {
		showUserTotal.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				UserTotalVisitor v = new UserTotalVisitor();
				//Count the number of users and display dialog box with results
				service.accept(v);
				JOptionPane.showMessageDialog(acpSingleton, "Number of Users: " + v.getUserTotal(), "User Count"
						, JOptionPane.INFORMATION_MESSAGE);
			}
		});
	}
	
	/**
	 * Get the ControlPanelShowControl singleton instance. Uses lazy instantiation.
	 * @return	Instance of ControlPanelShowControl.
	 */
	public static ControlPanelShowControl getInstance() {
		if (instance == null)
			instance = new ControlPanelShowControl();
		return instance;
	}
	
	/**
	 * Initializes the ActionListeners for all of the buttons.
	 */
	private void initActionListeners() {
		addListenerShowUserTotal();	//Initialize showUserTotal ActionListener
		addListenerShowGroupTotal();	//Initialize showGroupTotal ActionListener
		addListenerShowMsgTotal();	//Initialize showMsgTotal ActionListener
		addListenerShowPosPerc();	//Initialize showPosPerc ActionListener
	}
	
	/**
	 * Initialize the buttons for the panel.
	 */
	private void initShowButtons() {
		//Add the buttons to the panel
		add(showUserTotal);
		add(showGroupTotal);
		add(showMsgTotal);
		add(showPosPerc);
	}
}

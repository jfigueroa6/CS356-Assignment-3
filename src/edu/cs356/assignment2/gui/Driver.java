package edu.cs356.assignment2.gui;

public class Driver {

	public static void main(String[] args) {
		AdminControlPanel ACPSingleton = AdminControlPanel.getInstance();
		ACPSingleton.startACP();
	}

}

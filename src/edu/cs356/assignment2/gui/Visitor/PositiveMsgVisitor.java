package edu.cs356.assignment2.gui.Visitor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import edu.cs356.assignment2.service.TwitterGroupTree.GroupComponentsUser;
import edu.cs356.assignment2.service.TwitterGroupTree.UserGroup;
import edu.cs356.assignment2.service.TwitterUser.Tweet;

public class PositiveMsgVisitor implements Visitor {
	private static Map<String, String> positiveWords = null;	/**Stores all of the positive words. It's static so it only has to be filled once.*/
	private final static String fileLocation = "./Files/Positive_Words/positive_words.txt";	/**Location of positve_words file*/
	private int posCount = 0,
				totalMsg = 0;
	//=========================================================
	// Constructor
	//=========================================================
	public PositiveMsgVisitor() throws FileNotFoundException {
		//Will only be ran once
		if (positiveWords == null)
			fillPositiveWords();
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Get the percentage of positive messages.
	 * @return	Percentage of positive messages.
	 */
	public double getPositivePercentage() {
		try {
			return ((double)posCount / totalMsg) * 100;
		} catch (ArithmeticException e) {
			//Attempted to divide by 0
			return 0;
		}
	}
	
	/**
	 * Fills the static positiveWords map with the words from the positve_words file.
	 * This is only executed once so all PositiveMsgVisitors will have access to the map.
	 * @throws FileNotFoundException	Occurs when the positve_words file is not found.
	 */
	private void fillPositiveWords() throws FileNotFoundException {
		//Open a buffer to the word file
		Scanner fileIn = new Scanner(new FileInputStream(fileLocation));
		
		positiveWords = new HashMap<String, String>();
		
		//Since a single word is on a single line, get each line and put it into the map
		while (fileIn.hasNextLine())
			positiveWords.put(fileIn.nextLine().toLowerCase(), null);
		
		//Done so close the file
		fileIn.close();
	}

	/**
	 * Go through the leaf's user's news feed and check each message for positive words.
	 */
	@Override
	public void visitGroupComponentsUser(GroupComponentsUser leaf) {
		//Get the newsfeed
		List<Tweet> newsfeed = leaf.getUser(leaf.getID()).getNewsFeed();
		
		//Go through the tweets and check the words in each tweet for positive words.
		for (Tweet tweet : newsfeed) {
			Scanner t = new Scanner(tweet.getTweet());
			//Check each word in the tweet
			boolean done = false;	//Will become true if a positive word is found. No need to go through the entire tweet.
			while (t.hasNext() && !done) {
				String word = t.next();
				//Get rid of any characters that are not letters and make it lower case. Only care for the word.
				word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
				//Check if the word is in the map.
				if (positiveWords.containsKey(word)) {
					posCount++;
					done = true;
				}		
			}
			t.close();	//Close the scanner to prevent leak
			totalMsg++;	//Increase total message count.
		}

	}
	
	/**
	 * Checks the children of the group for news feeds since a group does not
	 * have a news feed.
	 */
	@Override
	public void visitUserGroup(UserGroup group) {
		for (int i = 0; i < group.getNumChild(); i++)
			group.getChild(i).accept(this);

	}

}

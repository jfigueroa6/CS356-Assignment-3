package edu.cs356.assignment2.service.TwitterUser;

public class Tweet {
	private String id;			/**ID of user that wrote tweet*/
	private String tweet;		/**Tweet*/
	private static int charLimit = 140;	/**Character limit for tweets defaulted to 140*/
	
	//=========================================================
	// Constructor
	//=========================================================
	/**
	 * Creates a new Tweet with the ID of the user that created the tweet
	 * and the actual tweet. It will limit the tweet to the set character
	 * limit.
	 * 
	 * @param id
	 * @param tweet
	 */
	public Tweet(String id, String tweet) {
		this.id = id;
		//Only allow a message with a character limit of charLimit
		if (tweet.length() > charLimit)
			this.tweet = tweet.substring(0, charLimit);
		else
			this.tweet = tweet;
	}
	
	//=========================================================
	// Methods
	//=========================================================
	/**
	 * Get ID of poster.
	 * @return	User ID
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * Get the posted tweet.
	 * @return	Tweet
	 */
	public String getTweet() {
		return tweet;
	}
	
	/**
	 * A static method that allows access to the set character
	 * limit.
	 * @return	Current character limit
	 */
	public static int getCharLimit() {
		return charLimit;
	}
	
	/**
	 * Static method that allows the change of the character limit
	 * to a different value. It will ensure that the limit is at least
	 * 1 character.
	 * @param newLimit
	 */
	public static void setCharLimit(int newLimit) {
		if (newLimit > 0)
			charLimit = newLimit; 
	}
	
	/**
	 * Converts the Tweet into a string with the user id and tweet.
	 * @return	String version of Tweet
	 */
	public String toString() {
		return id + ": " + tweet;
	}
}

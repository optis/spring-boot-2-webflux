package be.g00glen00b.apps.organizationapi.twitter;

import lombok.AllArgsConstructor;
import lombok.Data;
import twitter4j.Status;

/**
 * A simple representation of a tweet
 */
@AllArgsConstructor
@Data
public class Tweet {
	private String username;
	private String text;

	/**
	 * Method to convert the more complex <code>Status</code> object from Twitter4J to a simplified <code>Tweet</code>
	 */
	public static Tweet fromStatus(Status status) {
		return new Tweet(status.getUser().getName(), status.getText());
	}
}

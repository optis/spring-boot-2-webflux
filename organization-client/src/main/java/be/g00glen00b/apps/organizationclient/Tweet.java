package be.g00glen00b.apps.organizationclient;

import lombok.Data;

/**
 * We need to copy <code>Tweet</code> from the organization API to be able to consume the same type of data in our client.
 */
@Data
public class Tweet {
	private String username;
	private String text;
}

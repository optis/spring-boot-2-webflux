package be.g00glen00b.apps.organizationapi.twitter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Twitter authentication properties that should be configured. <code>@ConfigurationProperties</code> tells Spring boot
 * to use all properties starting with <code>twitter.*</code> to inject into this object for further use. Properties
 * can be defined in <code>application.properties</code>, environment variables, ... .
 */
@ConfigurationProperties(prefix = "twitter")
@Data
public class TwitterAPIConfigurationProperties {
	private String consumerKey;
	private String consumerSecret;
	private String accessToken;
	private String accessTokenSecret;
}

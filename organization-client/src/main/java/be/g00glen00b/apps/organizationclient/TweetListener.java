package be.g00glen00b.apps.organizationclient;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Class implementing <code>CommandLineRunner</code> so that the <code>run()</code> method will be invoked on startup.
 * We use <code>@AllArgsConstructor</code> to be able to inject the <code>WebClient</code> bean using Spring.
 */
@Component
@AllArgsConstructor
public class TweetListener implements CommandLineRunner {
	/**
	 * The SLF4J logger is used in the subscriber to see the tweets. We can't just use <code>.log()</code> from the
	 * publisher, because we need to subscribe in this case. In all other cases, Spring consumed the reactive stream
	 * for us, so we never had to.
	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private WebClient webClient;

	/**
	 * Method using the <code>WebClient</code> bean to call a specific URL using the <code>uriBuilder.pathSegment()</code>
	 * to build the URL. <code>WebClient</code> supports the use of server-sent events (SSE) as well.
	 */
	@Override
	public void run(String... args) {
		webClient
			.get()
			.uri(uriBuilder -> uriBuilder.pathSegment("organization", "TheLedger", "tweets").build())
			.retrieve()
			.bodyToFlux(Tweet.class)
			.map(Tweet::toString)
			.subscribe(logger::info);
	}
}

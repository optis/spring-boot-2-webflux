package be.g00glen00b.apps.organizationapi.twitter;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

import java.util.List;

@Service
@AllArgsConstructor
public class TwitterService {
	private Configuration configuration;

	/**
	 * Method to stream all tweets. Converts a Twitter4J <code>TwitterStream</code> into a <code>Flux</code> by using
	 * the <code>Flux.create()</code> method. The <code>Flux.create()</code> sink contains 4 important methods:
	 *
	 * - <code>sink.next()</code>: To publish the next object into the reactive stream.
	 * - <code>sink.error()</code>: To publish an exception.
	 * - <code>sink.complete()</code>: To complete a reactive stream. We're not using this because we have an infinite
	 *   stream.
	 * - <code>sink.onCancel()</code>: This can be used to shut down internals (like <code>TwitterStream</code> to
	 *   prevent memory leaks and other side-effects.
	 */
	public Flux<Tweet> findTweets(List<String> hashtags) {
		return Flux.create(sink -> {
			TwitterStream twitterStream = new TwitterStreamFactory(configuration).getInstance();
			twitterStream.onStatus(status -> sink.next(Tweet.fromStatus(status)));
			twitterStream.onException(sink::error);
			twitterStream.filter(hashtags.toArray(new String[0]));
			sink.onCancel(twitterStream::shutdown);
		});
	}
}
